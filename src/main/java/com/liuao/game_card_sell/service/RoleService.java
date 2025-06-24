package com.liuao.game_card_sell.service;

import com.liuao.game_card_sell.entity.Role;
import com.liuao.game_card_sell.mapper.RoleMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Resource
    private RoleMapper roleMapper;

    public List<Role> selectRoles(){
        return roleMapper.selectRoles();
    }
}
