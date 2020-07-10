package org.blankjee.cloudfast.service;

import org.blankjee.cloudfast.entity.ProcessLog;

import java.util.List;

/**
 * @Author blankjee
 * @Date 2020/7/9 19:54
 */
public interface ILogService {

    /**
     * 日志记录
     * @param processLog
     */
    void insertLog(ProcessLog processLog);

    /**
     * 查询历史操作记录
     * @param orderNo
     * @return
     */
    List<ProcessLog> queryOperLog(Long orderNo);
}
