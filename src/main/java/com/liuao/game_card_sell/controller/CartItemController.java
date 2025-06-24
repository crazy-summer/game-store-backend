package com.liuao.game_card_sell.controller;

import com.liuao.game_card_sell.dto.ApiResponse;
import com.liuao.game_card_sell.dto.request.CartItemRequest;
import com.liuao.game_card_sell.entity.CartItem;
import com.liuao.game_card_sell.service.CartItemService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
