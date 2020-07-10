package org.blankjee.cloudfast.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.blankjee.cloudfast.common.entity.PageBean;
import org.blankjee.cloudfast.entity.User;
import org.blankjee.cloudfast.entity.VacationOrder;
import org.blankjee.cloudfast.entity.VacationOrderVo;

import java.util.List;

/**
 * @Author blankjee
 * @Date 2020/7/7 15:34
 */
public interface IVacationOrderService {

    /**
     * 提交请假申请
     * @param vacationOrder
     */
    void insertVacationOrder(VacationOrder vacationOrder);

    /**
     * 请假单列表查询
     * @param pageBean
     * @return
     */
    Page<VacationOrderVo> queryVacationOrder(PageBean pageBean);

    /**
     * 根据审批单号查询审批信息
     * @param vacationId
     * @return
     */
    VacationOrder queryVacation(Long vacationId);

    /**
     * 更新审批单状态
     * @param vacationId
     * @param state
     */
    void updateState(Long vacationId, Integer state);

    /**
     * 提交申请
     * @param vacationId
     * @return
     */
    boolean submitApply(Long vacationId);

    /**
     * 删除审批单
     * @param vacationId
     */
    void delVacation(Long vacationId);
}
