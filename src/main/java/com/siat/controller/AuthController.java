package com.siat.controller;

import com.siat.Exception.UnauthorizedException;
import com.siat.entity.UserBean;
import com.siat.entity.ResponseBean;
import com.siat.service.AuthService;
import com.siat.util.JWTUtil;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api(
        tags = "/api/auth",
        description = "权限相关接口"
)
@Controller
@RequestMapping(path="api/auth")
public class AuthController extends BaseController {

    private AuthService authService;

    @Autowired
    public void setService(AuthService authService) {
        this.authService = authService;
    }

    // 登录接口
    @PostMapping("/login")
    public ResponseBean login(@RequestParam("username") String username,
                              @RequestParam("password") String password) {
        UserBean userBean = authService.getUser(username);
        if (userBean.getPassword().equals(password)) {
            return ResponseBean
                    .ok()
                    .result(JWTUtil.sign(username, password));
        } else {
            throw new UnauthorizedException();
        }
    }

    // 更新快过期的token
    @RequestMapping("/refresh")
    @RequiresAuthentication
    public ResponseBean refresh() {
        String oldToken = request.getHeader("Authorization");
        String newToken = authService.refreshToken(oldToken);

        return ResponseBean.ok()
                .result(newToken);
    }


    // 鉴权失败时会自动跳转该接口（不需要显式调用该接口）
    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseBean unauthorized() {
        return ResponseBean
                .error()
                .errorCode(401)
                .errorMsg("Unauthorized");
    }

}
