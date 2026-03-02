package com.liuao.game_card_sell.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserCartridgeRelation {
    private Long id;
    private Long userId;
    private Long cartridgeId;
    private Boolean isFavorite;
    private Boolean isWishlist;
    private Boolean isPlayed;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}