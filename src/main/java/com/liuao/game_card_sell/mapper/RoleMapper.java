package com.liuao.game_card_sell.mapper;

import com.liuao.game_card_sell.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper{
    Role selectByName(@Param("name") String name);

    List<Role> selectRolesByIds(List<Long> roleIds);

    List<Role> selectRoles();
}