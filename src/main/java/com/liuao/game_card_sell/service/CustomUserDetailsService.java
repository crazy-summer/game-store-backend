package com.liuao.game_card_sell.service;


import com.liuao.game_card_sell.common.Page;
import com.liuao.game_card_sell.dto.PageInfo;
import com.liuao.game_card_sell.dto.PageRequest;
import com.liuao.game_card_sell.entity.Role;
import com.liuao.game_card_sell.entity.User;
import com.liuao.game_card_sell.entity.UserRole;
import com.liuao.game_card_sell.exception.BusinessException;
import com.liuao.game_card_sell.mapper.RoleMapper;
import com.liuao.game_card_sell.mapper.UserMapper;
import com.liuao.game_card_sell.mapper.UserRoleMapper;
import com.liuao.game_card_sell.mapper.UserRoleRelationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserMapper userMapper;

    private final UserRoleMapper userRoleMapper;

    private final RoleMapper roleMapper;

    private final PasswordEncoder passwordEncoder;

    private final UserRoleRelationMapper userRoleRelationMapper;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库获取用户
        User user = userMapper.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // 构建用户权限列表
        Set<GrantedAuthority> authorities = new HashSet<>();
        List<UserRole> userRoles = userRoleMapper.selectUserRoleByUserId(user.getId());
        List<Long> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .toList();
        List<Role> roles = roleMapper.selectRolesByIds(roleIds);
        roles.forEach(role ->
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));

        user.setAuthorities(authorities);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);

        return user;
    }

    @Transactional
    public User registerUser(User user) {
        // 校验用户名是否存在
        if (userMapper.checkUsernameExists(user.getUsername()) > 0) {
            throw new IllegalArgumentException("用户名已存在");
        }
        // 校验邮箱是否存在
        if (userMapper.checkEmailExists(user.getEmail()) > 0) {
            throw new IllegalArgumentException("邮箱已注册");
        }


        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabledStatus(1); // 启用状态

        // 插入用户
        userMapper.insertUser(user);
        // 插入用户角色关联（需额外实现 UserRoleMapper，见下文）默认roleId=3，表示普通用户(roleId=1,"admin",roleId=2,"manager",
        //roleId=3, "user")
        userRoleMapper.insertUserRole(UserRole.genNormalUserRole(user.getId()));

        return user;
    }

    public Page<User> getUserList(int page, int size) {
        int offset = (page - 1) * size;
        List<User> userList = userMapper.selectPageWithRoles(offset, size);
        int total = userMapper.selectUserWithRolesCount();

        Page<User> pageResult = new Page<>(page, size, total);
        pageResult.setRecords(userList);

        return pageResult;
    }

    public User createUser(User user) {
        return null;
    }

    @Transactional
    public int updateUser(User user) {
        try{
            userMapper.updateUser(user);
            userRoleRelationMapper.deleteByUserId(user.getId());
            List<UserRole> userRoles = user.getRoles().stream()
                    .map(role -> UserRole.builder().userId(user.getId()).roleId(role.getId()).build())
                    .toList();
            userRoleRelationMapper.addRelation(userRoles);
            return 1;
        }catch (Exception e){
            log.error("[更新用户失败]", e);
            throw new BusinessException("400", "更新用户失败");
        }
    }

    public int deleteUser(Long id) {
        return 0;
    }
}