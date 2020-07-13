package org.blankjee.cloudfast.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.blankjee.cloudfast.common.entity.PageBean;
import org.blankjee.cloudfast.entity.TaskVo;

/**
 * @Author blankjee
 * @Date 2020/7/13 9:01
 */
public interface ITaskProceService {

    /**
     * 查询我的待办任务
     * @param pageBean
     * @return
     */
    Page<TaskVo> queryMyTask(PageBean pageBean);

    /**
     * 根据审批单号查询正在执行的流程任务
     * @param vacationId
     * @return
     */
    TaskVo queryTaskById(Long vacationId);

    /**
     * 流程办理
     * @param taskVo
     */
    void completeTask(TaskVo taskVo);
}
