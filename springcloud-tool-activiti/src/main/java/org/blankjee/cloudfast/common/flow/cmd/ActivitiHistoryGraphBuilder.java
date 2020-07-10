package org.blankjee.cloudfast.common.flow.cmd;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.HistoricActivityInstanceQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.cmd.GetDeploymentProcessDefinitionCmd;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.blankjee.cloudfast.common.flow.model.Edge;
import org.blankjee.cloudfast.common.flow.model.Graph;
import org.blankjee.cloudfast.common.flow.model.Node;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Desc 根据历史生成实时运行阶段的子图
 * @Author blankjee
 * @Date 2020/7/10 11:44
 */
@Slf4j
public class ActivitiHistoryGraphBuilder {

    private String processInstanceId;
    private ProcessDefinitionEntity processDefinitionEntity;
    private List<HistoricActivityInstance> historicActivityInstances;
    private List<HistoricActivityInstance> visitedHistoricActivityInstances;
    private Map<String, Node> nodeMap = new HashMap<>();

    public ActivitiHistoryGraphBuilder(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Graph build() {
        this.fetchProcessDefinitionEntity();
        this.fetchHistoricActivityInstances();
        Graph graph = new Graph();
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            Node currentNode = new Node();
            currentNode.setId(historicActivityInstance.getId());
            currentNode.setName(historicActivityInstance.getActivityName());
            currentNode.setType(historicActivityInstance.getActivityType());
            currentNode.setActive(historicActivityInstance.getEndTime() == null);
            log.debug("当前节点：{}:{}", currentNode.getName(), currentNode.getId());
            Edge previousEdge = this.findPreviousEdge(currentNode, historicActivityInstance.getStartTime().getTime());
            if (previousEdge == null) {
                if (graph.getInitial() == null) {
                    graph.setInitial(currentNode);
                }
            } else {
                log.debug("上一个节点：{}", previousEdge.getName());
            }
            nodeMap.put(currentNode.getId(), currentNode);
            visitedHistoricActivityInstances.add(historicActivityInstance);
        }
        if (graph.getInitial() == null) {
            throw new IllegalStateException("找不到初始化节点");
        }
        return graph;
    }

    /**
     * 根据流程实例ID获取对应的流程定义
     */
    public void fetchProcessDefinitionEntity() {
        String processDefinitionId = Context.getCommandContext()
                .getHistoricProcessInstanceEntityManager()
                .findHistoricProcessInstance(processInstanceId)
                .getProcessDefinitionId();
        GetDeploymentProcessDefinitionCmd cmd = new GetDeploymentProcessDefinitionCmd(processDefinitionId);
        processDefinitionEntity = cmd.execute(Context.getCommandContext());
    }

    /**
     * 获取历史流程实例
     */
    public void fetchHistoricActivityInstances() {
        HistoricActivityInstanceQueryImpl historicActivityInstanceQuery = new HistoricActivityInstanceQueryImpl();
        // 如果此处使用UUID会出现排序错误问题，但是如果用startTime，可能出现因为处理速度太快时间一样的次序问题。
        // historicActivityInstanceQuery.processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().asc();
        historicActivityInstanceQuery.processInstanceId(processInstanceId).orderByHistoricActivityInstanceId().asc();

        Page page = new Page(0, 100);
        historicActivityInstances = Context
                .getCommandContext()
                .getHistoricActivityInstanceEntityManager()
                .findHistoricActivityInstancesByQueryCriteria(historicActivityInstanceQuery, page
                );
    }

    /**
     * 找到这个节点前面的连线
     * @param currentNode
     * @param currentStartTime
     * @return
     */
    public Edge findPreviousEdge(Node currentNode, long currentStartTime) {
        String activityId = currentNode.getName();
        ActivityImpl activity = processDefinitionEntity.findActivity(activityId);
        HistoricActivityInstance nestestHistoricActivityInstance = null;
        String temporaryPvmTransitionId = null;

        for (PvmTransition pvmTransition : activity.getIncomingTransitions()) {
            PvmActivity source = pvmTransition.getSource();
            String previousActivityId = source.getId();
            HistoricActivityInstance visitedHistoryActivityInstance = this.findVisitedHistoricActivityInstance(previousActivityId);

            if (visitedHistoryActivityInstance == null) {
                continue;
            }
            // 如果上一个节点还未完成，说明不可能是从这个节点过来的，跳过。
            if (visitedHistoryActivityInstance.getEndTime() == null) {
                continue;
            }
            log.debug("当前节点开始时间：{}", new Date((currentStartTime)));
            log.debug("nestest节点结束时间：{}", visitedHistoryActivityInstance.getEndTime());

            // 如果当前节点的开始时间，比上一个节点结束时间要早，跳过。
            if (currentStartTime < visitedHistoryActivityInstance.getEndTime().getTime()) {
                continue;
            }
            if (nestestHistoricActivityInstance == null) {
                nestestHistoricActivityInstance = visitedHistoryActivityInstance;
                temporaryPvmTransitionId = pvmTransition.getId();
            } else if ((currentStartTime - nestestHistoricActivityInstance
                    .getEndTime().getTime()) > (currentStartTime - visitedHistoryActivityInstance
                    .getEndTime().getTime())) {
                // 寻找离当前节点最近的上一个节点，比较上一个节点的endTime与当前节点startTime的差。
                nestestHistoricActivityInstance = visitedHistoryActivityInstance;
                temporaryPvmTransitionId = pvmTransition.getId();
            }
        }
        // 没找到上一个节点，就返回null
        if (nestestHistoricActivityInstance == null) {
            return null;
        }
        Node previousNode = nodeMap.get(nestestHistoricActivityInstance.getId());
        if (previousNode == null) {
            return null;
        }
        log.debug("previousNode: {}:{}", previousNode.getName(), previousNode.getId());

        Edge edge = new Edge();
        edge.setName(temporaryPvmTransitionId);
        previousNode.getOutgoingEdges().add(edge);
        edge.setSrc(previousNode);
        edge.setDesc(currentNode);
        return edge;
    }

    /**
     * 根据ID获取历史节点信息
     * @param activityId
     * @return
     */
    public HistoricActivityInstance findVisitedHistoricActivityInstance(String activityId) {
        for (int i = visitedHistoricActivityInstances.size() - 1; i >= 0; i--) {
            HistoricActivityInstance historicActivityInstance = visitedHistoricActivityInstances.get(i);
            if (activityId.equals(historicActivityInstance.getActivityId())) {
                return historicActivityInstance;
            }
        }
        return null;
    }
}
