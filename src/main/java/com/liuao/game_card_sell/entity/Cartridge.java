package com.liuao.game_card_sell.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Cartridge {
    private Long id;

    private String title;

    @TableField(exist = false)
    private List<CartridgeCategory> cartridgeCategories;

    @TableField(exist = false)
    private List<CartridgePlatform> cartridgePlatforms;

    private Integer score;

    private String coverImagePath;

    private String coverImageUrl;

    private BigDecimal price;

    private int stock;

    private LocalDateTime releaseDate;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
