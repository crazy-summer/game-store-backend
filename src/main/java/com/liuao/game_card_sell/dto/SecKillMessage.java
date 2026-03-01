package com.liuao.game_card_sell.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class SecKillMessage {
    private long userId;
    private long cartridgeId;

    public SecKillMessage(long userId, long cartridgeId) {
        this.userId = userId;
        this.cartridgeId = cartridgeId;
    }
}