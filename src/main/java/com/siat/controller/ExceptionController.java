package com.siat.controller;

import com.siat.Exception.TokenValidationException;
import com.siat.Exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 异常控制器
@RestControllerAdvice
public class ExceptionController extends BaseController {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public void unauthorizedException(Throwable ex) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(TokenValidationException.class)
    public void tokenValidationException(Throwable ex) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
    }

    // 捕捉其他所有异常
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public void globalException(Throwable ex) throws IOException {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    }

}
