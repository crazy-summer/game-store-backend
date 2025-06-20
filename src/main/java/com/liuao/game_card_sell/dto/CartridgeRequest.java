package com.liuao.game_card_sell.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartridgeRequest {
    // 筛选条件
    private String searchText;

    private List<String> cartridgePlatformNames;
    private List<String> cartridgeCategoryNames;

    private Long userId;

    private Boolean isFavorite;

    private Boolean isWishList;

    private Boolean isPlayed;

    //排序条件
    private String sortField;

    private String sortDirection = "DESC";

    // 游标参数
    private Long cursorId;

    private Integer pageSize;
}
