package com.liuao.game_card_sell.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserCartridgeRelationRequest {
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @NotNull(message = "卡带id不能为空")
    private Long cartridgeId;

    private Boolean isFavorite;

    private Boolean isWishlist;

    private Boolean isPlayed;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}