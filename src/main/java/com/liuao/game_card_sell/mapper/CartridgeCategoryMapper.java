package com.liuao.game_card_sell.mapper;

import com.liuao.game_card_sell.entity.CartridgeCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartridgeCategoryMapper {
    public List<CartridgeCategory> selectCartridgeCategories();

    Boolean deleteByCartridgeId(Long id);

    Boolean addRelation(Long id, List<Long> categoryIds);

    List<CartridgeCategory> selectCartridgeCategoryDictByIds(List<Long> ids);
}

