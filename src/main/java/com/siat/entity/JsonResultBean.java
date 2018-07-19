package com.siat.entity;

public class JsonResultBean {

    private String status = null;  // 状态
    private Object result = null;  // 结果

    private JsonResultBean(String status) {
        this.status = status;
    }

    public static JsonResultBean ok() {
        return new JsonResultBean("ok");
    }

    public static JsonResultBean error() {
        return new JsonResultBean("error");
    }

    public JsonResultBean result(Object result) {
        this.result = result;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
