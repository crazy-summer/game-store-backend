package com.liuao.game_card_sell.dto;

import lombok.Data;

// PageRequest.java
@Data
public class PageRequest {
    private int page = 1; // 页码（从1开始）
    private int size = 10; // 每页条数

    // getters and setters
}