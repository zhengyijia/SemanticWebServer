package com.siat.controller;

import com.siat.Exception.UnauthorizedException;
import com.siat.entity.AuthEntity.AccountBean;
import com.siat.entity.AuthEntity.TokenBean;
import com.siat.service.AuthService;
import com.siat.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(
        tags = "/api/auth",
        description = "权限相关接口"
)
@RestController
@RequestMapping(path="api/auth")
public class AuthController extends BaseController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ApiOperation(
            value = "登录并获取token",
            notes = "执行登录操作，返回token信息",
            httpMethod = "POST"
    )
    @PostMapping("/login")
    public TokenBean login(@RequestBody final AccountBean accountBean) {
        if(authService.isValidAccount(accountBean)) {
            String jwt = JwtUtil.generateToken(accountBean.getUsername());
            return new TokenBean(jwt);
        }else {
            throw new UnauthorizedException("用户名或密码错误");
        }
    }

}
