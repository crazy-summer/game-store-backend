package com.liuao.game_card_sell.controller;

import com.liuao.game_card_sell.common.Page;
import com.liuao.game_card_sell.dto.ApiResponse;
import com.liuao.game_card_sell.dto.request.CartItemListRequest;
import com.liuao.game_card_sell.dto.request.CartItemRequest;
import com.liuao.game_card_sell.dto.response.CartItemExt;
import com.liuao.game_card_sell.entity.CartItem;
import com.liuao.game_card_sell.service.CartItemService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {
    @Resource
    private CartItemService cartItemService;

    @PostMapping("/add")
    public ApiResponse<CartItem> addCartItem(@RequestBody CartItemRequest request){
        CartItem cartItem = cartItemService.addCartItem(request);
        return ApiResponse.success(cartItem);
    }

    @GetMapping("/list")
    public ApiResponse<Page<CartItemExt>> getCartItemList(@Valid @ModelAttribute CartItemListRequest request){
        Page<CartItemExt> cartItemPage = cartItemService.selectPage(request);
        return ApiResponse.success(cartItemPage);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteCartItem(@PathVariable("id") Long id){
        boolean success = cartItemService.deleteCartItem(id);
        return ApiResponse.success(success);
    }

    @PutMapping("/{id}")
    public ApiResponse<CartItemExt> updateCartItem(@PathVariable("id") Long id, @RequestBody CartItemRequest request){
        CartItemExt cartItemExt = cartItemService.updateCartItem(id, request);
        return ApiResponse.success(cartItemExt);
    }
}
