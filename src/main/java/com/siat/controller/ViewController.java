package com.siat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// 视图控制器
@Controller
@RequestMapping(path="/")
public class ViewController {

    // 跳转到登录页
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String toLogin() {
        return "login";
    }

    // 跳转到首页
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String toIndex () {
        return "index";
    }

    // 跳转到URI信息页
    // 匹配localName不包含“.”字符
    @RequestMapping(value = "ontologies/{localName:[^.]+}", method = RequestMethod.GET)
    public String showUriInfo (@PathVariable(value = "localName") String localName) {

        return "uri_info";
    }

}
