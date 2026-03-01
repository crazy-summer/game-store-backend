package com.liuao.game_card_sell.dto.request;

import lombok.Data;

@Data
public class SecKillRequest {
    private long userId;
    private long cartId;
    private long cartridgeId;
}
