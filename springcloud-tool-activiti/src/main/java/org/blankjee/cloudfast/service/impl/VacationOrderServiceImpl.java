package org.blankjee.cloudfast.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ActivitiException;
import org.blankjee.cloudfast.common.entity.PageBean;
import org.blankjee.cloudfast.entity.FlowDef;
import org.blankjee.cloudfast.entity.User;
import org.blankjee.cloudfast.entity.VacationOrder;
import org.blankjee.cloudfast.entity.VacationOrderVo;
import org.blankjee.cloudfast.mapper.VacationOrderMapper;
import org.blankjee.cloudfast.service.IUserService;
import org.blankjee.cloudfast.service.IVacationOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public void insertVacationOrder(VacationOrder vacationOrder) {

    }

    @Override
    public Page<VacationOrderVo> queryVacationOrder(PageBean pageBean) {
//        Page<VacationOrderVo> page = new Page<>(pageBean.getPage(), pageBean.getLimit());
//        User currentUser = userService.getCurrentUser();
//        Page<VacationOrderVo> vacationOrderPage = vacationOrderMapper.queryVacationOrder(page, currentUser.getUserId());
//        return vacationOrderPage;
        return null;
    }

    @Override
    public Page<VacationOrder> queryList(PageBean pageBean) {
        Page<VacationOrder> page = new Page<>(pageBean.getPage(), pageBean.getLimit());
        Page<VacationOrder> vacationOrderPage = vacationOrderMapper.selectPage(page, null);
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
        return false;
    }

    @Override
    public void delVacation(Long vacationId) {

    }
}
