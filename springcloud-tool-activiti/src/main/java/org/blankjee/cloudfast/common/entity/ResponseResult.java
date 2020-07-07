package org.blankjee.cloudfast.common.entity;

/**
 * @Description 封装的返回结果集
 * @Author blankjee
 * @Date 2020/7/6 16:04
 */
public class ResponseResult<T> {
    // 返回状态码
    public int code;
    // 返回描述信息
    private String msg;
    // 返回内容体
    private T data;

    public ResponseResult<T> setCode(ResultCode resultCode) {
        this.code = resultCode.code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public ResponseResult<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseResult<T> setData(T data) {
        this.data = data;
        return this;
    }
}
