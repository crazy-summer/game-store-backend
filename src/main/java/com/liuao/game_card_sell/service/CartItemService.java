package com.liuao.game_card_sell.service;

import com.liuao.game_card_sell.common.Page;
import com.liuao.game_card_sell.dto.request.CartItemListRequest;
import com.liuao.game_card_sell.dto.request.CartItemRequest;
import com.liuao.game_card_sell.dto.response.CartItemExt;
import com.liuao.game_card_sell.entity.Cart;
import com.liuao.game_card_sell.entity.CartItem;
import com.liuao.game_card_sell.exception.BusinessException;
import com.liuao.game_card_sell.mapper.CartItemMapper;
import com.liuao.game_card_sell.mapper.CartMapper;
import com.liuao.game_card_sell.util.SecurityUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CartItemService {
    @Resource
    CartItemMapper cartItemMapper;

    @Resource
    CartMapper cartMapper;

    @Resource SecurityUtil securityUtil;

    @Transactional
    public CartItem addCartItem(CartItemRequest request){
        try{
            validateCurrentUser(request.getUserId());
            Cart cart = cartMapper.selectCartByUserId(request.getUserId());
            if (cart == null){
                cart = new Cart();
                try{
                    cart.setUserId(request.getUserId());
                    cartMapper.insertUserCart(cart);
                }catch (DuplicateKeyException e){
                    // 其他线程已创建，重新查询
                    cart = cartMapper.selectCartByUserId(request.getUserId());
                }
            }
            int rows = cartItemMapper.insertCartItem(cart.getId(),request.getCartridgeId(),request.getQuantity());
            if (rows > 0){
                CartItem cartItem = new CartItem();
                cartItem.setCartId(cart.getId());
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

    public Page<CartItemExt> selectPage(CartItemListRequest request) {
        try{
            validateCurrentUser(request.getUserId());
            Long userId = request.getUserId();
            Integer pageNumber = request.getPageNumber();
            Integer pageSize = request.getPageSize();
            int count = cartItemMapper.selectPageCount(userId);
            int offset = (pageNumber - 1) * pageSize;
            List<CartItemExt> cartItems = cartItemMapper.selectCartItemPage(userId, offset ,pageSize);
            Page<CartItemExt> page = new Page<>(pageNumber, pageSize, count);
            page.setRecords(cartItems);
            return page;
        }
        catch (BusinessException e){
            log.error("[查询用户购物车项目列表失败]", e);
            throw e;
        }
        catch (Exception e){
            log.error("[查询用户购物车项目列表失败]", e);
            throw new BusinessException("500", "系统内部错误");
        }
    }

    public boolean deleteCartItem(Long id) {
        try{
            int rows = cartItemMapper.deleteCartItem(id);
            if (rows > 0){
                return true;
            }
            else{
                throw new BusinessException("400", "删除购物车项目失败");
            }
        }
        catch (BusinessException e){
            log.error("[删除购物车项目失败]", e);
            throw e;
        }
        catch (Exception e){
            log.error("[删除购物车项目失败]", e);
            throw new BusinessException("500", "系统内部错误");
        }
    }

    public CartItemExt updateCartItem(Long id, CartItemRequest request) {
        try {
            validateCurrentUser(request.getUserId());
            int rows = cartItemMapper.updateCartItem(id, request);
            if (rows > 0){
                CartItemExt cartItemExt = new CartItemExt();
                cartItemExt.setId(id);
                cartItemExt.setQuantity(request.getQuantity());
                return cartItemExt;
            }
            else {
                throw new BusinessException("400", "更新购物车项目失败");
            }
        }
        catch (BusinessException e){
            log.error("[更新购物车项目失败]", e);
            throw e;
        }
        catch (Exception e){
            log.error("[更新购物车项目失败]", e);
            throw new BusinessException("500", "系统内部错误");
        }
    }

    private void validateCurrentUser(Long givenUserId) {
        Long userId = securityUtil.getCurrentUserId();
        if (!Objects.equals(userId, givenUserId)){
            throw new BusinessException("400", "传入的userId和当前用户userId不一致");
        }
    }
}
