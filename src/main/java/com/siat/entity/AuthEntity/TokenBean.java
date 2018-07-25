package com.siat.entity.AuthEntity;

public class TokenBean {

    private String token;

    public TokenBean() { }

    public TokenBean(String token) {
        setToken(token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
