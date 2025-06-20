package com.liuao.game_card_sell.entity;

import lombok.Data;

@Data
public class CartridgeCategoryRelation {
    private Long id;

    private Long cartridgeId;

    private Long categoryId;
}
