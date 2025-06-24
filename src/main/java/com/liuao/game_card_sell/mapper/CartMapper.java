package com.liuao.game_card_sell.mapper;

import com.liuao.game_card_sell.dto.request.CartItemRequest;
import com.liuao.game_card_sell.entity.Cart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartMapper {
    Cart selectCartByUserId(Long userId);

    Integer insertUserCart(Cart cart);
}
