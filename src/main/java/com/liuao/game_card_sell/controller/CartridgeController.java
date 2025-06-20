package com.liuao.game_card_sell.controller;

import com.liuao.game_card_sell.dto.ApiResponse;
import com.liuao.game_card_sell.dto.CartridgeRequest;
import com.liuao.game_card_sell.dto.response.CartridgeResponse;
import com.liuao.game_card_sell.entity.Cartridge;
import com.liuao.game_card_sell.entity.CartridgeCategory;
import com.liuao.game_card_sell.entity.CartridgePlatform;
import com.liuao.game_card_sell.entity.UpdateCartridgeRequest;
import com.liuao.game_card_sell.service.CartridgeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/cartridges")
public class CartridgeController {

    @Resource
    private CartridgeService cartridgeService;

    @Operation(summary = "查询卡带列表") // 标注接口功能描述
    @PostMapping("/list")
    public ResponseEntity<ApiResponse<CartridgeResponse>> getCartridges(@RequestBody CartridgeRequest request){
        CartridgeResponse response = cartridgeService.selectCartridges(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/platforms")
    public ResponseEntity<ApiResponse<List<CartridgePlatform>>> getCartridgePlatforms(){
        List<CartridgePlatform> cartridgePlatforms = cartridgeService.selectCartridgePlatforms();
        return ResponseEntity.ok(ApiResponse.success(cartridgePlatforms));
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<CartridgeCategory>>> getCartridgeCategories(){
        List<CartridgeCategory> cartridgeCategories = cartridgeService.selectCartridgeCategories();
        return ResponseEntity.ok(ApiResponse.success(cartridgeCategories));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Cartridge>> getCartridgeById(@PathVariable @NotNull Long id){
        Cartridge cartridge = cartridgeService.selectCartridgeById(id);
        return ResponseEntity.ok(ApiResponse.success(cartridge));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Cartridge>> updateCartridge(
            @PathVariable @NotNull Long id,
            @Valid @RequestBody UpdateCartridgeRequest updateCartridgeRequest){
        // 添加详细日志
        log.info("收到更新请求 - ID: {}, 请求体: {}", id, updateCartridgeRequest);
        Cartridge cartridge = cartridgeService.updateCartridgeById(id, updateCartridgeRequest);
        return ResponseEntity.ok(ApiResponse.success(cartridge));
    }
}
