package com.siat.service;

import com.siat.entity.AuthEntity.AccountBean;

public interface AuthService {

    // 验证用户输入的账号密码是否正确
    boolean isValidAccount(AccountBean accountBean);

}
