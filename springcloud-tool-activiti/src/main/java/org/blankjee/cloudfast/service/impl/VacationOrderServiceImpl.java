package org.blankjee.cloudfast.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.blankjee.cloudfast.common.entity.PageBean;
import org.blankjee.cloudfast.entity.VacationOrder;
import org.blankjee.cloudfast.entity.VacationOrderVo;
import org.blankjee.cloudfast.service.IVacationOrderService;
import org.springframework.stereotype.Service;

/**
 * @Author blankjee
 * @Date 2020/7/7 19:26
 */
@Service
@Slf4j
public class VacationOrderServiceImpl implements IVacationOrderService {

    @Override
    public void insertVacationOrder(VacationOrder vacationOrder) {

    }

    @Override
    public Page<VacationOrderVo> queryVacationOrder(PageBean pageBean) {
        return null;
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
        return false;
    }

    @Override
    public void delVacation(Long vacationId) {

    }
}
