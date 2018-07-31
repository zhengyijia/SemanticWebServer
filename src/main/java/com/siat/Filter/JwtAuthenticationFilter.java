package com.siat.Filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siat.Exception.TokenValidationException;
import com.siat.entity.ErrorBean;
import com.siat.util.ErrorCode;
import com.siat.util.JwtUtil;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 权限管理参考博客：https://www.jianshu.com/p/e62af4a217eb
 */
@Order(1) // 优先级
@WebFilter(filterName = "JwtAuthenticationFilter", urlPatterns = "/*")
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, TokenValidationException {
        try {
            if(isProtectedUrl(request)) {
                //检查jwt令牌, 如果令牌不合法或者过期, 里面会直接抛出异常, 下面的catch部分会直接返回
                request = JwtUtil.validateTokenAndAddUserIdToHeader(request);
            }
        } catch (Exception e) {
            // TODO: 2018/7/31 有没有办法直接抛出错误，在ExceptionController中统一处理？
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            ErrorBean errorBean =
                    new ErrorBean.Builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .errorCode(ErrorCode.AUTH_ERROR_TOKEN)
                    .errorMsg(e.getMessage())
                    .build();
            ObjectMapper objectMapper = new ObjectMapper();
            writer.print(
                    objectMapper.writeValueAsString(errorBean)
            );
            writer.flush();
            return;
        }
        //如果jwt令牌通过了检测, 那么就把处理过的request传递给后面的RESTful api
        filterChain.doFilter(request, response);
    }

    // 过滤规则
    private boolean isProtectedUrl(HttpServletRequest request) {
        if (pathMatcher.match("/api/auth/login", request.getServletPath()))
            return false;

        return pathMatcher.match("/api/**", request.getServletPath());
    }

}
