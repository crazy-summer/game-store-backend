package com.liuao.game_card_sell.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDto {
    private Long userId;

    private String username;

    private List<String> roles;

    public static UserDto fromUserDetails(UserDetails userDetails){
        User u = (User) userDetails;
        List<String> roles = userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.startsWith("ROLE_"))
                .map(auth -> auth.substring(5))
                .toList();
        UserDto userDto = new UserDto();
        userDto.setUsername(userDetails.getUsername());
        userDto.setUserId(u.getId());
        userDto.setRoles(roles);
        return userDto;
    }
}
