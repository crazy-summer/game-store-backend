package com.liuao.game_card_sell.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderItem {
    private long id;
    private long orderId;
    private long cartridgeId;
    private Double price;
    private int quantity;
    private LocalDateTime createTime;
}
