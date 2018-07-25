package com.siat.controller;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 控制器基类，所有控制器继承该基类
public abstract class BaseController {

    HttpServletRequest request;
    HttpServletResponse response;
    HttpSession session;

    @ModelAttribute
    public void init(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();

        // 重新设置服务器端编码为"utf-8"（默认是ISO-8859-1）
        response.setCharacterEncoding("utf-8");
    }

}
