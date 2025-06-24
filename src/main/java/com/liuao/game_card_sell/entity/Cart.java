package com.liuao.game_card_sell.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Cart {
    private Long cartId;

    private Long userId;

    private LocalDateTime createTime;
}
