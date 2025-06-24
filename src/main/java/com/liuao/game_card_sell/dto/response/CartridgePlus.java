package com.liuao.game_card_sell.dto.response;

import com.liuao.game_card_sell.entity.Cartridge;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CartridgePlus extends Cartridge {
    private Boolean isFavorite;

    private Boolean isPlayed;

    private Boolean isWishList;
}
