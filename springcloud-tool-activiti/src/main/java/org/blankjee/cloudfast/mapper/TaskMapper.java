package org.blankjee.cloudfast.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.blankjee.cloudfast.entity.TaskVo;

/**
 * @Author blankjee
 * @Date 2020/7/13 9:05
 */
public interface TaskMapper extends BaseMapper<TaskVo> {

    /**
     * 查询我的的待办任务
     * @param page
     * @param userId
     * @return
     */
    Page<TaskVo> queryMyTask(Page<TaskVo> page, @Param("userId") String userId);

    /**
     * 查询审批当前任务信息
     * @param vacationId
     * @return
     */
    TaskVo queryTaskById(@Param("vacationId") Long vacationId);
}
