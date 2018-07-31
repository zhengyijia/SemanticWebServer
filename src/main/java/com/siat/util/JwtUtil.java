package com.siat.util;

import com.siat.Exception.TokenValidationException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

public class JwtUtil {

    private static final long EXPIRATION_TIME = 24*60*60*1000; // JWT有效期24小时
    private static final String SECRET = "SiatSecret";  // 生成JWT第三部分签名Signature的密钥
    private static final String TOKEN_PREFIX = "Bearer ";  // JWT前缀
    private static final String HEADER_STRING = "Authorization";  // 请求头的token字段
    private static final String USER_NAME = "user_name";  // 将用户名添加到JWT的Playload（荷载信息）中

    /**
     * 根据用户名生成jwt token
     * @param username
     * @return
     */
    public static String generateToken(String username) {
        HashMap<String, Object> map = new HashMap<>();  // JWT的第二部分Playload（荷载信息）
        map.put(USER_NAME, username);
        String jwt = Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))// 1000 hour
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        return TOKEN_PREFIX + jwt; // JWT前面一般都会加Bearer
    }

    /**
     * 验证token并在header添加用户名
     * @param request http请求
     * @return header添加用户名后的http请求
     */
    public static HttpServletRequest validateTokenAndAddUserIdToHeader(HttpServletRequest request) throws TokenValidationException{
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            try {
                Map<String, Object> body = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody();
                return new CustomHttpServletRequest(request, body);
            } catch (Exception e) {
                LogUtil.e(e.getMessage());
                throw new TokenValidationException(e.getMessage());
            }
        } else {
            throw new TokenValidationException("Missing token");
        }
    }

    // 用于往请求的header中添加字段
    public static class CustomHttpServletRequest extends HttpServletRequestWrapper {
        private Map<String, String> claims;

        public CustomHttpServletRequest(HttpServletRequest request, Map<String, ?> claims) {
            super(request);
            this.claims = new HashMap<>();
            claims.forEach((k, v) -> this.claims.put(k, String.valueOf(v)));
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            if (claims != null && claims.containsKey(name)) {
                return Collections.enumeration(Arrays.asList(claims.get(name)));
            }
            return super.getHeaders(name);
        }

        public Map<String, String> getClaims() {
            return claims;
        }
    }

}
