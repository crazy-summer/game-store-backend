package com.liuao.game_card_sell.controller;

import com.liuao.game_card_sell.dto.ApiResponse;
import com.liuao.game_card_sell.dto.PageInfo;
import com.liuao.game_card_sell.dto.PageRequest;
import com.liuao.game_card_sell.entity.User;
import com.liuao.game_card_sell.entity.UserDto;
import com.liuao.game_card_sell.service.CustomUserDetailsService;
import com.liuao.game_card_sell.dto.ResponseResult;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.liuao.game_card_sell.common.Page;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Resource
    private CustomUserDetailsService customUserDetailsService;

    @Resource
    private AuthenticationManager authenticationManager;

    // 获取用户列表（需要ADMIN权限）
    @GetMapping("/admin/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseResult<Page<User>>> getUserList(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size
    ) {
        Page<User> userPage = customUserDetailsService.getUserList(page, size);
        return ResponseEntity.ok(ResponseResult.success(userPage));
    }

    // 创建用户
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseResult<User> createUser(@RequestBody User user) {
        User createdUser = customUserDetailsService.createUser(user);
        return ResponseResult.success(createdUser);
    }

    // 更新用户
    @PutMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Integer> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        int result = customUserDetailsService.updateUser(user);
        return result > 0 ? ApiResponse.success(result) : ApiResponse.error(400, "更新失败");
    }

    // 删除用户
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseResult<Integer> deleteUser(@PathVariable Long id) {
        int result = customUserDetailsService.deleteUser(id);
        return result > 0 ? ResponseResult.success(result) : ResponseResult.error("删除失败");
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseResult<UserDto>> getProfile() {
        // 从 SecurityContextHolder 中获取当前已认证用户的信息
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        // 通过用户名从数据库中获取用户信息
        User user = customUserDetailsService.loadUserByUsername(username);
        UserDto userDto = UserDto.fromUserDetails(user);
        return ResponseEntity.ok(ResponseResult.success(userDto));
    }
}