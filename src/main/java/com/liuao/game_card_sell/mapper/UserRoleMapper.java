package com.liuao.game_card_sell.mapper;

import com.liuao.game_card_sell.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleMapper {
    int insertUserRole(UserRole userRole);

    List<UserRole> selectUserRoleByUserId(Long userId);
}
