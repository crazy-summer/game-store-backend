package com.liuao.game_card_sell.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemRequest {
    @NotNull(message = "用户id不能为null")
    private Long userId;

    @NotNull(message = "卡带id不能为null")
    private Long cartridgeId;

    @NotNull(message = "数量不能为null")
    @Min(value = 1, message = "数量至少为1")
    private Integer quantity;
}
