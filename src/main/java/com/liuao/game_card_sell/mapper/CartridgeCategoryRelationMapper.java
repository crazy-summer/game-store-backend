package com.liuao.game_card_sell.mapper;

import com.liuao.game_card_sell.entity.CartridgeCategoryRelation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartridgeCategoryRelationMapper {
    public List<CartridgeCategoryRelation> selectRelationByCartridgeId(Long cartridgeId);
}
