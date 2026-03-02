package com.liuao.game_card_sell.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuao.game_card_sell.dto.response.CartridgePlus;
import com.liuao.game_card_sell.entity.Cartridge;
import com.liuao.game_card_sell.entity.UpdateCartridgeRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CartridgeMapper extends BaseMapper<Cartridge> {
    public List<CartridgePlus> selectCartridges(
            String searchText,
            @Param("platformNames") List<String> cartridgePlatformNames,
            @Param("categoryNames")List<String> cartridgeCategoryNames,
            Long userId,
            Boolean isFavorite,
            Boolean isPlayed,
            Boolean isWishList,
            String sortField,
            String sortDirection,
            Long cursorId,
            Integer pageSize
    );

    Cartridge selectCartridgeById(Long id);

    Boolean updateCartridgeById(Long id, @Param("request") UpdateCartridgeRequest updateCartridgeRequest);

    BigDecimal selectPriceById(long cId);
}
