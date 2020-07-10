package org.blankjee.cloudfast.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.blankjee.cloudfast.common.util.CommonUtil;
import org.blankjee.cloudfast.entity.ProcessLog;
import org.blankjee.cloudfast.entity.User;
import org.blankjee.cloudfast.mapper.ProcessLogMapper;
import org.blankjee.cloudfast.service.ILogService;
import org.blankjee.cloudfast.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author blankjee
 * @Date 2020/7/9 19:56
 */
@Service
public class LogServiceImpl implements ILogService {

    @Resource
    private ProcessLogMapper processLogMapper;

    @Resource
    private IUserService userService;

    @Override
    public void insertLog(ProcessLog processLog) {
        User currentUser = userService.getCurrentUser();
        processLog.setLogId(CommonUtil.genId());
        processLog.setCreateTime(DateUtil.date());
        processLog.setOperId(currentUser.getUserName());
        processLogMapper.insert(processLog);
    }

    @Override
    public List<ProcessLog> queryOperLog(Long orderNo) {
        QueryWrapper<ProcessLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ORDER_NO", orderNo);
        queryWrapper.orderByDesc("CREATE_TIME");
        return processLogMapper.selectList(queryWrapper);
    }
}
