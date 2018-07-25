package com.siat.Exception;

// 用户密码验证失败
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super("UnauthorizedException");
    }

    public UnauthorizedException(String msg) {
        super(msg);
    }

}
