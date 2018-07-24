package com.siat.entity;

public class ResponseBean {

    private String status = null;     // 状态
    private Object result = null;     // 结果
    private ErrorMsg errorMsg = null;   // 错误信息

    private ResponseBean(String status) {
        this.status = status;
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

    public Object getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(ErrorMsg errorMsg) {
        this.errorMsg = errorMsg;
    }

    private static class ErrorMsg {
        int code;
        String msg = null;

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
    }

    public static ResponseBean ok() {
        return new ResponseBean("ok");
    }

    public static ResponseBean error() {
        ResponseBean responseBean = new ResponseBean("error");
        responseBean.setErrorMsg(new ErrorMsg());

        return responseBean;
    }

    public ResponseBean result(Object result) {
        this.setResult(result);

        return this;
    }

    public ResponseBean errorCode(int code) {
        errorMsg.setCode(code);

        return this;
    }

    public ResponseBean errorMsg(String msg) {
        errorMsg.setMsg(msg);

        return this;
    }

}
