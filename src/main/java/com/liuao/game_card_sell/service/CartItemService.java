package com.liuao.game_card_sell.service;

import com.liuao.game_card_sell.dto.request.CartItemRequest;
import com.liuao.game_card_sell.entity.Cart;
import com.liuao.game_card_sell.entity.CartItem;
import com.liuao.game_card_sell.exception.BusinessException;
import com.liuao.game_card_sell.mapper.CartItemMapper;
import com.liuao.game_card_sell.mapper.CartMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CartItemService {
    @Resource
    CartItemMapper cartItemMapper;

    @Resource
    CartMapper cartMapper;

    @Transactional
    public CartItem addCartItem(CartItemRequest request){
        try{
            Cart cart = cartMapper.selectCartByUserId(request.getUserId());
            if (cart == null){
                cart = new Cart();
                try{
                    cart.setUserId(request.getUserId());
                }catch (DuplicateKeyException e){
                    // 其他线程已创建，重新查询
                    cart = cartMapper.selectCartByUserId(request.getUserId());
                }
                cartMapper.insertUserCart(cart);
            }
            int rows = cartItemMapper.insertCartItem(cart.getCartId(),request.getCartridgeId(),request.getQuantity());
            if (rows > 0){
                CartItem cartItem = new CartItem();
                cartItem.setCartId(cart.getCartId());
                cartItem.setCartridgeId(request.getCartridgeId());
                cartItem.setQuantity(request.getQuantity());
                return cartItem;
            } else{
                throw new BusinessException("400", "添加购物车项失败");
            }
        }
        catch (BusinessException e){
            log.error("[添加购物车项失败]", e);
            throw e;
        }
        catch (Exception e){
            log.error("[新增用户购物项目失败]", e);
            throw new BusinessException("500", "系统内部错误");
        }
    }
}
