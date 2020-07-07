package org.blankjee.cloudfast.common.config;

import lombok.extern.slf4j.Slf4j;
import org.blankjee.cloudfast.common.entity.ResponseResult;
import org.blankjee.cloudfast.common.entity.ResponseUtil;
import org.blankjee.cloudfast.common.entity.ResultCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author blankjee
 * @Date 2020/7/6 15:51
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义的业务异常
     * @param e
     * @param <T>
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    public <T> ResponseResult<T> bizExceptionHandler(BizException e) {
        log.error("业务异常：{}", e.getMsg());
        return ResponseUtil.makeErrRsp(e.getCode(), "业务处理异常：" + e.getMsg());
    }

    /**
     * 处理空指针异常
     * @param e
     * @param <T>
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    public <T> ResponseResult exceptionHandler(NullPointerException e) {
        log.error("空指针异常：{}", e);
        return ResponseUtil.makeErrRsp(ResultCode.FAIL.code, "空指针异常：" + e.getMessage());
    }

    /**
     * 处理其他异常
     * @param req
     * @param e
     * @param <T>
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public <T> ResponseResult exceptionHandler(HttpServletRequest req, Exception e) {
        log.error("系统异常：{}", e);
        return ResponseUtil.makeErrRsp(ResultCode.INTERNAL_SERVER_ERROR.code, "系统异常：" + e.getMessage());
    }
}
