package com.liuao.game_card_sell.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = System.getenv("JWT_SECRET_KEY");

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10小时

    // 生成 SecretKey 对象
    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // 生成 JWT
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256) // 使用新 API
                .compact();
    }

    // 验证 JWT
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // 从 JWT 中提取用户名
    public String extractUsername(String token) {
        return Jwts.parserBuilder() // 使用新的 parserBuilder
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 检查 JWT 是否过期
    private boolean isTokenExpired(String token) {
        return Jwts.parserBuilder() // 使用新的 parserBuilder
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}