package com.wstro.common.dto;

public class JsonResultDto<T> {

    private int code;

    private String msg;

    private T data;

    public JsonResultDto() {}

    public JsonResultDto(int code, String msg) {
        this(code, msg, null);
    }

    public JsonResultDto(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
