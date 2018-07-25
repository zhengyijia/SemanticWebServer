package com.siat.entity.AuthEntity;

// 用户登录时的账户bean
public class AccountBean {

    private String username;  // 用户名
    private String password;  // 密码

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
