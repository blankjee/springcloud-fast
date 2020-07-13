package org.blankjee.cloudfast.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.TaskService;
import org.blankjee.cloudfast.common.entity.PageBean;
import org.blankjee.cloudfast.common.entity.SysConstant;
import org.blankjee.cloudfast.entity.ProcessLog;
import org.blankjee.cloudfast.entity.TaskVo;
import org.blankjee.cloudfast.entity.User;
import org.blankjee.cloudfast.mapper.TaskMapper;
import org.blankjee.cloudfast.service.ILogService;
import org.blankjee.cloudfast.service.ITaskProceService;
import org.blankjee.cloudfast.service.IUserService;
import org.blankjee.cloudfast.service.IVacationOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author blankjee
 * @Date 2020/7/13 9:03
 */
@Service
@Slf4j
public class TaskProceServiceImpl implements ITaskProceService {

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private IUserService userService;

    @Resource
    private TaskService taskService;

    @Resource
    private IVacationOrderService vacationOrderService;

    @Resource
    private ILogService logService;

    @Override
    public Page<TaskVo> queryMyTask(PageBean pageBean) {
        Page<TaskVo> page = new Page<>(pageBean.getPage(), pageBean.getLimit());
        User currentUser = userService.getCurrentUser();
        return taskMapper.queryMyTask(page, currentUser.getUserId());
    }

    @Override
    public TaskVo queryTaskById(Long vacationId) {
        return taskMapper.queryTaskById(vacationId);
    }

    @Override
    public void completeTask(TaskVo taskVo) {
        Map<String, Object> variables = new HashMap<>();
        String spState = "";
        String spContext = "";
        if (StrUtil.equals("0", taskVo.getApprovalType())) {
            // 审核通过
            spState = SysConstant.APPROVAL_AGREE;
            spContext = "审批通过";
            variables.put("spState", spState);
        } else if (StrUtil.equals("1", taskVo.getApprovalType())) {
            // 驳回
            vacationOrderService.updateState(Long.valueOf(taskVo.getVacationId()), SysConstant.SUBMITTED_STATE);
            spState = SysConstant.APPROVAL_REJECT;
            spContext = "审批未通过";
            variables.put("spState", spState);
        }
        taskService.complete(taskVo.getTaskId(), variables);
        // 记录日志
        ProcessLog bean = new ProcessLog();
        User user = userService.getCurrentUser();
        bean.setOrderNo(Long.valueOf(taskVo.getVacationId()));
        bean.setTaskId(taskVo.getTaskId());
        bean.setTaskName(taskVo.getTaskName());
        bean.setTaskKey(taskVo.getTaskDefKey());
        bean.setApprovStatu(spState);
        bean.setOperValue(user.getUserName() + spContext + "，审批意见：" + taskVo.getRemark());
        logService.insertLog(bean);
    }
}
