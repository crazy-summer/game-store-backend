package com.liuao.game_card_sell.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Cartridge {
    private Long id;

    private String title;

    private List<CartridgeCategory> cartridgeCategories;

    private List<CartridgePlatform> cartridgePlatforms;

    private Integer score;

    private String coverImagePath;

    private String coverImageUrl;

    private Double price;

    private LocalDateTime releaseDate;

    private LocalDateTime createTime;
}
