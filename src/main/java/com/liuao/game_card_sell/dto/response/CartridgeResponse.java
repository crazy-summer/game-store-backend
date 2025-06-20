package com.liuao.game_card_sell.dto.response;

import com.liuao.game_card_sell.entity.Cartridge;
import lombok.Data;

import java.util.List;

@Data
public class CartridgeResponse {
    private List<Cartridge> cartridges;

    private Boolean hasNext;
}
