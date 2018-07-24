package com.siat.service;

import com.siat.database.DataSource;
import com.siat.entity.UserBean;
import com.siat.util.JWTUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

// 模拟数据库查询
@Service
public class AuthService {

    // 从数据库中获取用户信息
    // TODO: 2018/7/24 修改为从数据库中获取
    public UserBean getUser(String username) {
        // 没有此用户直接返回null
        if (! DataSource.getData().containsKey(username))
            return null;

        UserBean user = new UserBean();
        Map<String, String> detail = DataSource.getData().get(username);

        user.setUsername(username);
        user.setPassword(detail.get("password"));
        for (String role: detail.get("role").split(",")) {
            user.addRole(role);
        }
        for (String permission: detail.get("permission").split(",")) {
            user.addPermission(permission);
        }
        return user;
    }

    public String refreshToken(String oldToken) {
        return JWTUtil.refresh(oldToken);
    }

}
