package com.liuao.game_card_sell.filter;

import jakarta.servlet.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SecurityContextLoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        System.out.println("Before filter: " + SecurityContextHolder.getContext().getAuthentication());
        chain.doFilter(request, response);
        System.out.println("After filter: " + SecurityContextHolder.getContext().getAuthentication());
    }
    // 其他方法...
}