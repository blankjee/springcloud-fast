package org.blankjee.cloudfast.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.activiti.engine.task.Task;
import org.blankjee.cloudfast.common.entity.PageBean;
import org.blankjee.cloudfast.entity.FlowDef;
import org.blankjee.cloudfast.entity.FlowMain;
import org.blankjee.cloudfast.entity.FlowRule;

import java.util.List;
import java.util.Map;

/**
 * @Author blankjee
 * @Date 2020/7/7 15:11
 */
public interface IFlowInfoService {
    /**
     * 查询流程定义列表
     * @return
     */
    List<FlowDef> queryFlowDefList();

    /**
     * 给定ID查询流程定义
     * @param defId
     * @return
     */
    FlowDef queryFlowDef(Long defId);

    /**
     * 新增流程定义
     * @param flowDef
     */
    void insertFlowDef(FlowDef flowDef);

    /**
     * 新增流程规则
     * @param flowRule
     * @return
     */
    String insertFlowRule(FlowRule flowRule);

    /**
     * 流程规则查询
     * @param pageBean
     * @return
     */
    Page<FlowRule> queryFlowRule(PageBean pageBean);

    /**
     * 流程匹配服务
     * @param orderId
     * @param variable
     * @return
     */
    String resolve(Long orderId, Map<String, Object> variable);

    /**
     * 启动流程
     * @param flowMain
     * @param variable
     * @return
     */
    String runFlow(FlowMain flowMain, Map<String, Object> variable);

    /**
     * 记录流程主表信息
     * @param flowMain
     */
    void insertFlowMain(FlowMain flowMain);

    /**
     * 根据流程梳实例查询当前任务信息
     * @param processInstanceId
     * @return
     */
    Task queryTaskByInstId(String processInstanceId);

    /**
     * 根据审批单号查询匹配流程信息
     * @param orderNo
     * @return
     */
    FlowMain queryFlowMainByOrderNo(Long orderNo);

    /**
     * 根据主键查询
     * @param flowInstId
     * @return
     */
    FlowMain queryFlowById(Long flowInstId);

    /**
     * 根据ID删除流程规则
     * @param flowInstId
     */
    void delFlowRuleBy(Long flowInstId);
}
