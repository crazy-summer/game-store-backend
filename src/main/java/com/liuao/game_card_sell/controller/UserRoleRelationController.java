package com.liuao.game_card_sell.controller;

import com.liuao.game_card_sell.dto.ApiResponse;
import com.liuao.game_card_sell.entity.Role;
import com.liuao.game_card_sell.service.RoleService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/userRoleRelation")
public class UserRoleRelationController {
    @Resource
    private RoleService roleService;

    @GetMapping("/roles")
    public ApiResponse<List<Role>> getRoles(){
        return ApiResponse.success(roleService.selectRoles());
    }
}
