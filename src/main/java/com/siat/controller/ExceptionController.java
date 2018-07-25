package com.siat.controller;

import com.siat.Exception.TokenValidationException;
import com.siat.Exception.UnauthorizedException;
import com.siat.entity.ErrorBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 异常控制器
@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ErrorBean unauthorizedException(Throwable ex) {
        return new ErrorBean(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(TokenValidationException.class)
    public ErrorBean tokenValidationException(Throwable ex) {
        return new ErrorBean(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorBean globalException(Throwable ex) {
        return new ErrorBean(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

}
