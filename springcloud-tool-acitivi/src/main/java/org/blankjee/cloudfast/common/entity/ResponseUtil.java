package org.blankjee.cloudfast.common.entity;

/**
 * @Author blankjee
 * @Date 2020/7/6 16:36
 */
public class ResponseUtil {
    private final static String SUC_MSG = "操作成功";

    public static <T> ResponseResult<T> makeOkRsp() {
        return new ResponseResult<T>().setCode(ResultCode.SUCCESS).setMsg(SUC_MSG);
    }

    public static <T> ResponseResult<T> makeOkRsp(int code, T data) {
        return new ResponseResult<T>().setCode(code).setMsg(SUC_MSG).setData(data);
    }

    public static <T> ResponseResult<T> makeOkRsp(T data) {
        return new ResponseResult<T>().setCode(ResultCode.SUCCESS).setMsg(SUC_MSG).setData(data);
    }

    public static <T> ResponseResult<T> makeErrRsp(int code, String msg) {
        return new ResponseResult<T>().setCode(code).setMsg(msg);
    }

    public static <T> ResponseTableResult<T> makeTableRsp(int code, long count, T data) {
        return new ResponseTableResult<T>().setCode(code).setCount(count).setMsg(SUC_MSG).setData(data);
    }
}
