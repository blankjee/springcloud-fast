package org.blankjee.cloudfast.common.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author blankjee
 * @Date 2020/7/6 15:42
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class BizException extends RuntimeException {
    // 状态码
    protected int code;
    // 返回信息
    protected String msg;
}
