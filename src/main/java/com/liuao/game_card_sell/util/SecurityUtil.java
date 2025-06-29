package com.liuao.game_card_sell.util;

import com.liuao.game_card_sell.filter.JwtRequestFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {
    private final JwtUtil jwtUtil;

    public SecurityUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * 从当前认证信息中获取用户ID
     */
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getCredentials() == null) {
            return null;
        }

        // 从认证信息中获取JWT令牌
        String token = (String) authentication.getCredentials();
        if (token == null) {
            return null;
        }

        // 从JWT中提取用户ID
        return jwtUtil.extractUserId(token);
    }

    /**
     * 从JWT令牌中获取用户ID
     */
    public Long getUserIdFromToken(String token) {
        return jwtUtil.extractUserId(token);
    }
}