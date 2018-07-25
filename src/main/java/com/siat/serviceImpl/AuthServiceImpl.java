package com.siat.serviceImpl;

import com.siat.entity.AuthEntity.AccountBean;
import com.siat.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    /**
     * 验证用户输入的账号密码是否正确
     * @param accountBean 登录账户信息
     * @return 正确True/错误False
     */
    public boolean isValidAccount(AccountBean accountBean) {
        return "admin".equals(accountBean.getUsername())
                && "admin".equals(accountBean.getPassword());
    }

}
