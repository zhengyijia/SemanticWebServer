package com.siat.controller;

import com.siat.exception.TokenValidationException;
import com.siat.exception.UnauthorizedException;
import com.siat.entity.ErrorBean;
import com.siat.util.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

// 异常控制器
@RestControllerAdvice
public class ExceptionController extends BaseController {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ErrorBean unauthorizedException(Throwable ex) throws IOException {
        return new ErrorBean.Builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .errorCode(ErrorCode.AUTH_ERROR_USERNAME_PASSWORD)
                .errorMsg(ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(TokenValidationException.class)
    public ErrorBean tokenValidationException(Throwable ex) throws IOException {
        return new ErrorBean.Builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .errorCode(ErrorCode.AUTH_ERROR_TOKEN)
                .errorMsg(ex.getMessage())
                .build();
    }

    // 捕捉其他所有异常
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorBean globalException(Throwable ex) throws IOException {
        return new ErrorBean.Builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorCode(ErrorCode.UNDEFINDED_ERROR_TYPE)
                .errorMsg(ex.getMessage())
                .build();
    }

}
