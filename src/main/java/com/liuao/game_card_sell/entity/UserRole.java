package com.liuao.game_card_sell.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRole {
    private Long userId;

    private Long roleId;

    public static UserRole genNormalUserRole(Long userId){
        return UserRole.builder()
                .userId(userId)
                .roleId(3L)
                .build();
    }
}
