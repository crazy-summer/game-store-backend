package com.liuao.game_card_sell.entity;

import lombok.Data;

@Data
public class CartridgePlatformRelation {
    private Long id;

    private Long cartridgeId;

    private Long platformId;
}
