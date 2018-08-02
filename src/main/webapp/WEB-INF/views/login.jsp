<%--
  Created by IntelliJ IDEA.
  User: zheng
  Date: 2018/7/12
  Time: 19:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" %>
<%@ include file="./include/taglibs.jsp"%>
<html>
<head>
    <title>登录</title>
    <script src="${ctx}/static/js/jquery-3.3.1.js"></script>
    <script src="${ctx}/static/js/jquery.cookie.js"></script>
</head>
<style>
</style>
<body>
<form>
    管理员：admin/admin<br>
    用户名：<input type="text" id="username" value="admin" /><br>
    密码：<input type="password" id="password" value="admin" /><br>
    <input type="button"  id="login_button"  value="登录">
</form>
</body>
<script>
    "use strict";

    $("#login_button").click(function () {
        var username = $("#username").val();
        var password = $("#password").val();

        $.ajax({
            url : "${ctx}/api/auth/login",
            data : JSON.stringify({
                "username" : username,
                "password" : password
            }),
            dataType : "json",
            contentType: "application/json;charset=utf-8",
            type : "POST",
            success : function(result) {
                // 保存数据到cookie
                $.cookie('token', result.token);

                window.location.href='${ctx}/index';
            },
            error : function() {
                alert("登录失败！");
            }
        });
    })
</script>
</html>
