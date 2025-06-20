package com.liuao.game_card_sell.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.liuao.game_card_sell.entity.Role;
import com.liuao.game_card_sell.entity.User;
import com.liuao.game_card_sell.entity.UserRole;
import com.liuao.game_card_sell.mapper.RoleMapper;
import com.liuao.game_card_sell.mapper.UserMapper;
import com.liuao.game_card_sell.mapper.UserRoleMapper;
import com.liuao.game_card_sell.service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRoleMapper userRoleMapper;

    @Mock
    private RoleMapper roleMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPassword("password123");
        testUser.setEmail("test@example.com");
    }

    @Test
    public void testRegisterUser_Success() {
        // 模拟用户名和邮箱检查不存在
        when(userMapper.checkUsernameExists(anyString())).thenReturn(0);
        when(userMapper.checkEmailExists(anyString())).thenReturn(0);

        // 模拟密码加密
        when(passwordEncoder.encode(anyString())).thenReturn("encryptedPassword");

        // 模拟插入用户后设置ID
        doAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L); // 设置用户ID
            return null;
        }).when(userMapper).insertUser(any(User.class));

        // 执行注册
        User registeredUser = userDetailsService.registerUser(testUser);

        // 验证结果
        assertNotNull(registeredUser.getId());
        assertEquals("encryptedPassword", registeredUser.getPassword());
        assertTrue(registeredUser.getEnabled() == 1);

        // 验证数据库操作
        verify(userMapper, times(1)).checkUsernameExists("testuser");
        verify(userMapper, times(1)).checkEmailExists("test@example.com");
        verify(userMapper, times(1)).insertUser(argThat(user ->
                user.getUsername().equals("testuser") &&
                        user.getPassword().equals("encryptedPassword") &&
                        user.getEnabled() == 1
        ));
        verify(userRoleMapper, times(1)).insertUserRole(argThat(role ->
                role.getUserId() == 1L &&
                        role.getRoleId() == 3L // 普通用户角色ID
        ));
    }

    @Test
    public void testRegisterUser_UsernameExists() {
        // 模拟用户名已存在
        when(userMapper.checkUsernameExists(anyString())).thenReturn(1);

        // 执行注册并验证异常
        assertThrows(IllegalArgumentException.class, () -> {
            userDetailsService.registerUser(testUser);
        }, "用户名已存在");

        // 验证仅检查了用户名，未进行后续操作
        verify(userMapper, times(1)).checkUsernameExists("testuser");
        verify(userMapper, never()).checkEmailExists(anyString());
        verify(userMapper, never()).insertUser(any(User.class));
        verify(userRoleMapper, never()).insertUserRole(any(UserRole.class));
    }

    @Test
    public void testRegisterUser_EmailExists() {
        // 模拟用户名不存在，但邮箱已存在
        when(userMapper.checkUsernameExists(anyString())).thenReturn(0);
        when(userMapper.checkEmailExists(anyString())).thenReturn(1);

        // 执行注册并验证异常
        assertThrows(IllegalArgumentException.class, () -> {
            userDetailsService.registerUser(testUser);
        }, "邮箱已注册");

        // 验证检查了用户名和邮箱，未进行后续操作
        verify(userMapper, times(1)).checkUsernameExists("testuser");
        verify(userMapper, times(1)).checkEmailExists("test@example.com");
        verify(userMapper, never()).insertUser(any(User.class));
        verify(userRoleMapper, never()).insertUserRole(any(UserRole.class));
    }

    @Test
    @Transactional
    public void testRegisterUser_TransactionRollbackOnFailure() {
        // 模拟用户名和邮箱检查不存在
        when(userMapper.checkUsernameExists(anyString())).thenReturn(0);
        when(userMapper.checkEmailExists(anyString())).thenReturn(0);

        // 模拟插入用户后抛出异常
        doThrow(new RuntimeException("Simulated error")).when(userRoleMapper).insertUserRole(any(UserRole.class));

        // 执行注册并验证异常
        assertThrows(RuntimeException.class, () -> {
            userDetailsService.registerUser(testUser);
        });

        // 验证用户插入被回滚（通过 verifyNoInteractions 或其他方式）
        verify(userMapper, times(1)).insertUser(any(User.class));
        verify(userRoleMapper, times(1)).insertUserRole(any(UserRole.class));

        // 注意：实际验证事务回滚需要更复杂的设置（如使用 TestEntityManager）
    }
}