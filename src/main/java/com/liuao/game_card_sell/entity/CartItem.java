package com.liuao.game_card_sell.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CartItem {
    private Long id;

    private Long cartId;

    private Long cartridgeId;

    private Integer quantity;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
