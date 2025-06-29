package com.liuao.game_card_sell.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemListRequest {
    @NotNull(message = "用户id不能为null")
    private Long userId;

    @NotNull(message = "页码不能为null")
    @Min(value = 1, message = "页码至少为1")
    private Integer pageNumber;

    @NotNull(message = "每页大小不能为null")
    @Min(value = 1, message = "每页大小至少为1")
    private Integer pageSize;
}
