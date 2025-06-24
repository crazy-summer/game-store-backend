package com.liuao.game_card_sell.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartItemMapper {
    Integer insertCartItem(Long cartId, Long cartridgeId, Integer quantity);
}
