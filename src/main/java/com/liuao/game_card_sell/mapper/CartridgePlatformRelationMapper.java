package com.liuao.game_card_sell.mapper;

import com.liuao.game_card_sell.entity.CartridgePlatformRelation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartridgePlatformRelationMapper {
    public List<CartridgePlatformRelation> selectRelationByCartridgeId(Long cartridgeId);
}
