package com.siat.Exception;

// token验证失败
public class TokenValidationException extends RuntimeException {

    public TokenValidationException() {
        super("TokenValidationException");
    }

    public TokenValidationException(String msg) {
        super(msg);
    }

}
