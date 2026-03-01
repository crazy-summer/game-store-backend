package com.liuao.game_card_sell.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Order {
    private long id;
    private long userId;
    private Double totalPrice;
    private int status;
    private LocalDateTime createTime;
}
