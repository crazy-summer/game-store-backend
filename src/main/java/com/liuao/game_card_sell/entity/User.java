// User.java
package com.liuao.game_card_sell.entity;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
public class User implements UserDetails {
    private Long id;

    @NotNull(message = "用户名不能为空")
    @Size(min = 2, max = 20, message = "用户名长度必须在2到20个字符之间")
    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$",
            message = "用户名只能包含字母、数字和汉字")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 32, message = "密码长度必须在6-32个字符之间")
    private String password;

    @NotNull(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 手机号（可为null，但格式必须正确）
     */
    @Pattern(
            regexp = "^1[3-9]\\d{9}$",  // 不允许空字符串，只允许符合格式的非空值
            message = "手机号格式不正确"
    )
    @Null(message = "手机号必须为null")  // 仅允许为null，否则报错
    private String phone;

    @Range(min = 0, max = 1, message = "状态只能为0或1")
    private Integer enabledStatus;

    @NotEmpty
    private List<Role> roles;

    private LocalDateTime createTime;


    // 安全接口相关字段（使用 transient 避免序列化）
    private transient Collection<? extends GrantedAuthority> authorities;

    private Boolean accountNonExpired;

    private Boolean accountNonLocked;

    private Boolean credentialsNonExpired;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabledStatus != null && enabledStatus == 1;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired != null ? accountNonExpired : true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked != null ? accountNonLocked : true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired != null ? credentialsNonExpired : true;
    }
}

