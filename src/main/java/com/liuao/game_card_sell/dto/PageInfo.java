package com.liuao.game_card_sell.dto;

import java.util.List;

// PageInfo.java（MyBatis-Plus提供，或自定义）
public class PageInfo<T> {
    private List<T> records; // 当前页数据
    private long total; // 总记录数
    // getters and setters
}