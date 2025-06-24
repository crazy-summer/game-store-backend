package com.liuao.game_card_sell.mapper;

import com.liuao.game_card_sell.entity.UserCartridgeRelation;
import com.liuao.game_card_sell.entity.UserCartridgeRelationRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserCartridgeRelationMapper {
    int upsert(@Param("request") UserCartridgeRelationRequest request);

    UserCartridgeRelation selectUserCartridgeRelation(Long userId, Long cartridgeId);
}