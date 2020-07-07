package org.blankjee.cloudfast.common.entity;

/**
 * @Description Table结果的封装
 * @Author blankjee
 * @Date 2020/7/6 16:20
 */
public class ResponseTableResult<T> {
    // 返回状态码
    public int code;
    // 返回描述信息
    public String msg;
    // 返回总条数
    private long count;
    // 返回内容体
    private T data;

    public ResponseTableResult<T> setCode(ResultCode resultCode) {
        this.code = resultCode.code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public ResponseTableResult<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseTableResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseTableResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    public ResponseTableResult<T> setCount(long count) {
        this.count = count;
        return this;
    }

    public long getCount() {
        return count;
    }

}
