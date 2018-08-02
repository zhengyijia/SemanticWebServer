package com.siat.util;

// 设置错误码
public class ErrorCode {

    // Auth 相关错误
    public static final String AUTH_ERROR_USERNAME_PASSWORD = "000001";  // 用户名或密码错误
    public static final String AUTH_ERROR_TOKEN = "000002";              // token校验失败

    // 未定义错误统一使用该errorCode
    public static final String UNDEFINDED_ERROR_TYPE = "999999";         // 没有定义的错误类型

}
