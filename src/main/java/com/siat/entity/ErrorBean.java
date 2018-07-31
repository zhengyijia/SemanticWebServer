package com.siat.entity;

public class ErrorBean {

    private int status;
    private String errorCode;
    private String errorMsg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static class Builder {
        private int status;
        private String errorCode;
        private String errorMsg;

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder errorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Builder errorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
            return this;
        }

        public ErrorBean build() {
            return new ErrorBean(this);
        }
    }

    private ErrorBean(Builder builder) {
        this.status = builder.status;
        this.errorCode = builder.errorCode;
        this.errorMsg = builder.errorMsg;
    }
}
