package com.liuao.game_card_sell.mapper;

import com.liuao.game_card_sell.dto.request.CartItemRequest;
import com.liuao.game_card_sell.dto.response.CartItemExt;
import com.liuao.game_card_sell.entity.CartItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartItemMapper {
    Integer insertCartItem(Long cartId, Long cartridgeId, Integer quantity);

    int selectPageCount(Long userId);

    List<CartItemExt> selectCartItemPage(Long userId, Integer offset, Integer pageSize);

    int deleteCartItem(Long id);

    int updateCartItem(Long id, CartItemRequest request);

    CartItemExt selectCartItemById(Long id);
}
