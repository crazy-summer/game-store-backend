package com.liuao.game_card_sell.mapper;

import com.liuao.game_card_sell.entity.CartridgePlatform;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartridgePlatformMapper {
    public List<CartridgePlatform> selectCartridgePlatforms();

    Boolean deleteByCartridgeId(Long id);

    Boolean addRelation(Long id, List<Long> platformIds);

    List<CartridgePlatform> selectCartridgePlatformDictByIds(List<Long> ids);
}
