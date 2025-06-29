package com.liuao.game_card_sell.dto.response;

import com.liuao.game_card_sell.entity.CartItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CartItemExt extends CartItem {
    private String title;

    private String coverImageUrl;

    private Double price;
}
