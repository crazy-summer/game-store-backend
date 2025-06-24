package com.liuao.game_card_sell.controller;

import com.liuao.game_card_sell.dto.ApiResponse;
import com.liuao.game_card_sell.entity.UserCartridgeRelation;
import com.liuao.game_card_sell.entity.UserCartridgeRelationRequest;
import com.liuao.game_card_sell.service.UserCartridgeRelationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/cartridges")
public class UserCartridgeRelationController {
    @Resource
    private UserCartridgeRelationService userCartridgeRelationService;

    @Operation(summary = "更新用户与卡带关系")
    @PutMapping("/userCartridgeRelation")
    public ResponseEntity<ApiResponse<Boolean>> updateUserCartridgeRelation(@Valid @RequestBody UserCartridgeRelationRequest request) {
        Boolean success = userCartridgeRelationService.updateUserCartridgeRelation(request);
        return ResponseEntity.ok(ApiResponse.success(success));
    }

    @GetMapping("/userCartridgeRelation")
    public ResponseEntity<ApiResponse<UserCartridgeRelation>> getUserCartridgeRelation(
            @NotNull @RequestParam("userId")Long userId,@NotNull @RequestParam("cartridgeId") Long cartridgeId){
        UserCartridgeRelation userCartridgeRelation = userCartridgeRelationService.selectUserCartridgeRelation(userId, cartridgeId);
        return ResponseEntity.ok(ApiResponse.success(userCartridgeRelation));
    }
}
