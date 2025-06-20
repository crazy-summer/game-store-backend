// JWT 认证入口点（处理未授权请求）
package com.liuao.game_card_sell.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        System.err.println("触发认证入口点. 请求路径: " + request.getRequestURI());
        System.err.println("认证异常: " + authException.getMessage());
        System.err.println("当前上下文: " +
                SecurityContextHolder.getContext().getAuthentication());

        // 返回结构化的错误响应
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(
                "{\"error\":\"Unauthorized\",\"message\":\"" + authException.getMessage() + "\"}"
        );
    }
}