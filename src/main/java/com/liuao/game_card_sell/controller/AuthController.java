// AuthController.java
package com.liuao.game_card_sell.controller;

import com.liuao.game_card_sell.dto.ApiResponse;
import com.liuao.game_card_sell.dto.AuthenticationRequest;
import com.liuao.game_card_sell.dto.AuthenticationResponse;
import com.liuao.game_card_sell.dto.RegisterRequest;
import com.liuao.game_card_sell.entity.User;
import com.liuao.game_card_sell.entity.UserDto;
import com.liuao.game_card_sell.service.CustomUserDetailsService;
import com.liuao.game_card_sell.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;


@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "登录及注册接口") // 标注 Controller 所属模块
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService customUserDetailsService;

    private final JwtUtil jwtUtil;

    @Operation(summary = "用户注册") // 标注接口功能描述
    @PostMapping("/api/auth/register")
    public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody RegisterRequest request) {
        log.info("开始注册用户, 用户名:{}, 邮箱: {}", request.getUsername(), request.getEmail());
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        customUserDetailsService.registerUser(user);
        return ResponseEntity.ok(ApiResponse.success(200, "注册成功"));
    }

    // 用户登录接口
    @Operation(summary = "用户登录") // 标注接口功能描述
    @PostMapping("/api/auth/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>>
    createAuthenticationToken(@RequestBody AuthenticationRequest request) throws Exception {
        log.info("用户: {} 正在尝试登录", request.getUsername());
        try {
            // 验证用户名和密码
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        // 生成 JWT
        final User userDetails = customUserDetailsService.loadUserByUsername(request.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails, userDetails.getId());

        // 生成用户信息
        UserDto user = UserDto.fromUserDetails(userDetails);
        // 返回 JWT
        ApiResponse<AuthenticationResponse> response = ApiResponse.success(new AuthenticationResponse(jwt, user));
        return ResponseEntity.ok(response);
    }
}