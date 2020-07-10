package org.blankjee.cloudfast.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.blankjee.cloudfast.common.entity.PageBean;
import org.blankjee.cloudfast.common.entity.SysConstant;
import org.blankjee.cloudfast.common.util.CommonUtil;
import org.blankjee.cloudfast.entity.*;
import org.blankjee.cloudfast.mapper.VacationOrderMapper;
import org.blankjee.cloudfast.service.IFlowInfoService;
import org.blankjee.cloudfast.service.ILogService;
import org.blankjee.cloudfast.service.IUserService;
import org.blankjee.cloudfast.service.IVacationOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author blankjee
 * @Date 2020/7/7 19:26
 */
@Service
@Slf4j
public class VacationOrderServiceImpl implements IVacationOrderService {

    @Resource
    private VacationOrderMapper vacationOrderMapper;

    @Resource
    private IUserService userService;

    @Resource
    private IFlowInfoService flowInfoService;

    @Resource
    private TaskService taskService;

    @Resource
    private ILogService logService;

    @Override
    @Transactional
    public void insertVacationOrder(VacationOrder vacationOrder) {
        // 记录日志
        ProcessLog bean = new ProcessLog();
        User currentUser = userService.getCurrentUser();
        if (vacationOrder.getVacationId() != null) {
            // 更新
            vacationOrderMapper.updateById(vacationOrder);
            bean.setOrderNo(vacationOrder.getVacationId());
            bean.setOperValue(currentUser.getUserName());
        } else {
            long orderNo = CommonUtil.genId();
            bean.setOrderNo(orderNo);
            vacationOrder.setVacationId(orderNo);
            vacationOrder.setVacationState(0);
            vacationOrder.setUserId(currentUser.getUserId());
            vacationOrder.setCreateTime(DateUtil.date());
            vacationOrder.setSystemCode("1001");
            vacationOrder.setBusiType("2001");
            vacationOrderMapper.insert(vacationOrder);
            bean.setOperValue(currentUser.getUserName() + "填写审批单");
        }
        logService.insertLog(bean);
    }

    @Override
    public Page<VacationOrderVo> queryVacationOrder(PageBean pageBean) {
        Page<VacationOrder> page = new Page<>(pageBean.getPage(), pageBean.getLimit());
        User currentUser = userService.getCurrentUser();
        Page<VacationOrderVo> vacationOrderPage = vacationOrderMapper.queryVacationOrder(page, currentUser.getUserId());
        return vacationOrderPage;
    }

    @Override
    public VacationOrder queryVacation(Long vacationId) {
        return null;
    }

    @Override
    public void updateState(Long vacationId, Integer state) {

    }

    @Override
    public boolean submitApply(Long vacationId) {
        // 匹配流程并指定申请人
        Map<String, Object> variables = new HashMap<>();
        User currentUser = userService.getCurrentUser();
        String flowId = "";
        // 匹配流程之前查询是否已经匹配过了
        FlowMain flowMain = flowInfoService.queryFlowMainByOrderNo(vacationId);
        if (ObjectUtil.isNull(flowMain)) {
            variables.put("applyuser", currentUser.getUserId());
            flowId = flowInfoService.resolve(vacationId, variables);
        } else {
            flowId = String.valueOf(flowMain.getFlowId());
        }
        if (StrUtil.isBlank(flowId)) {
            return false;
        }
        // 流程流转，对应工作流提交成功
        Task task = flowInfoService.queryTaskByInstId(flowId);
        if (ObjectUtil.isNull(task)) {
            return false;
        }
        variables.put("subStates", "success");
        log.info("------->当前办理任务ID：{}", task.getId());
        taskService.complete(task.getId(), variables);
        // 更新审批单状态
        this.updateState(vacationId, SysConstant.REVIEW_STATE);
        // 记录日志
        ProcessLog bean = new ProcessLog();
        User user = userService.queryUserById(currentUser.getParentUserId());
        bean.setOrderNo(vacationId);
        bean.setTaskId(task.getId());
        bean.setTaskName(task.getName());
        bean.setTaskKey(task.getTaskDefinitionKey());
        bean.setOperValue(currentUser.getUserName() + "提交申请，待【" + user.getUserName() + "】审核。");
        logService.insertLog(bean);
        return true;
    }

    @Override
    public void delVacation(Long vacationId) {

    }
}
