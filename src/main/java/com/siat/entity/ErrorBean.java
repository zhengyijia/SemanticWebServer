package com.siat.entity;

public class ErrorBean {

    private int code;
    private String msg = null;     // 错误信息

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String error) {
        this.msg = error;
    }

    public ErrorBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
