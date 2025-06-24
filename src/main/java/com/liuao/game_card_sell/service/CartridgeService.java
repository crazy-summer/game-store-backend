package com.liuao.game_card_sell.service;

import com.liuao.game_card_sell.dto.CartridgeRequest;
import com.liuao.game_card_sell.dto.response.CartridgePlus;
import com.liuao.game_card_sell.dto.response.CartridgeResponse;
import com.liuao.game_card_sell.entity.*;
import com.liuao.game_card_sell.exception.BusinessException;
import com.liuao.game_card_sell.mapper.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CartridgeService {
    @Resource
    private CartridgeMapper cartridgeMapper;

    @Resource
    private CartridgePlatformMapper cartridgePlatformMapper;

    @Resource
    private CartridgeCategoryMapper cartridgeCategoryMapper;

    @Resource
    private CartridgePlatformRelationMapper cartridgePlatformRelationMapper;

    @Resource
    private CartridgeCategoryRelationMapper cartridgeCategoryRelationMapper;

    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of("id", "score", "price", "release_date");

    public CartridgeResponse selectCartridges(CartridgeRequest request){
        // 如果不是允许的排序字段，那么默认设置成id，避免sql注入
        if (!ALLOWED_SORT_FIELDS.contains(request.getSortField())) {
            request.setSortField("id"); // 默认字段
        }
        List<CartridgePlus> cartridges = cartridgeMapper.selectCartridges(
                request.getSearchText(),
                request.getCartridgePlatformNames(),
                request.getCartridgeCategoryNames(),
                request.getUserId(),
                request.getIsFavorite(),
                request.getIsPlayed(),
                request.getIsWishList(),
                request.getSortField(),
                request.getSortDirection(),
                request.getCursorId(),
                request.getPageSize() + 1 // 从数据库多查一条数据，如果数据总条数大于pageSize，说明还有下一页，否则没有下一页。
        );
        Integer pageSize = request.getPageSize();
        boolean hasNext = false;
        // 从数据库多查一条数据，如果数据总条数大于pageSize，说明还有下一页，否则没有下一页。
        if(cartridges.size() > pageSize){
            hasNext = true;
            cartridges.remove(cartridges.size() - 1);
        }
        CartridgeResponse response = new CartridgeResponse();
        response.setCartridges(cartridges);
        response.setHasNext(hasNext);
        return response;
    }

    public List<CartridgePlatform> selectCartridgePlatforms() {
        try{
            return cartridgePlatformMapper.selectCartridgePlatforms();
        }
        catch (Exception e){
            throw new BusinessException("400", "获取游戏平台字典错误", e);
        }
    }

    public List<CartridgeCategory> selectCartridgeCategories() {
        try{
            return cartridgeCategoryMapper.selectCartridgeCategories();
        }
        catch (Exception e){
            throw new BusinessException("400", "获取游戏分类字典错误", e);
        }
    }

    public Cartridge selectCartridgeById(Long id) {
        try{
            Cartridge cartridge = cartridgeMapper.selectCartridgeById(id);
            List<CartridgePlatformRelation> cartridgePlatformRelations = cartridgePlatformRelationMapper.selectRelationByCartridgeId(cartridge.getId());
            List<CartridgeCategoryRelation> cartridgeCategoryRelations = cartridgeCategoryRelationMapper.selectRelationByCartridgeId(cartridge.getId());
            List<Long> platformIds = cartridgePlatformRelations.stream()
                    .map(CartridgePlatformRelation::getPlatformId)
                    .toList();
            List<Long> categoryIds = cartridgeCategoryRelations.stream()
                    .map(CartridgeCategoryRelation::getCategoryId)
                    .toList();
            List<CartridgePlatform> cartridgePlatforms = platformIds.size() > 0
                    ? cartridgePlatformMapper.selectCartridgePlatformDictByIds(platformIds)
                    : Collections.emptyList();
            List<CartridgeCategory> cartridgeCategories = categoryIds.size() > 0
                    ? cartridgeCategoryMapper.selectCartridgeCategoryDictByIds(categoryIds)
                    : Collections.emptyList();
            cartridge.setCartridgePlatforms(cartridgePlatforms);
            cartridge.setCartridgeCategories(cartridgeCategories);
            return cartridge;
        }catch (Exception e){
            log.error("[根据id查询卡带详情错误], 错误详情: {}", e.getMessage());
            throw new BusinessException("400", "根据id查询卡带详情错误", e);
        }
    }

    @Transactional
    public Cartridge updateCartridgeById(Long id, UpdateCartridgeRequest updateCartridgeRequest) {
        try{
            cartridgeMapper.updateCartridgeById(id, updateCartridgeRequest);
            cartridgePlatformMapper.deleteByCartridgeId(id);
            cartridgeCategoryMapper.deleteByCartridgeId(id);
            cartridgePlatformMapper.addRelation(id, updateCartridgeRequest.getPlatformIds());
            cartridgeCategoryMapper.addRelation(id, updateCartridgeRequest.getCategoryIds());
            return cartridgeMapper.selectCartridgeById(id);
        }catch (Exception e){
            log.info("[根据id更新卡带详情错误], message: {}", e.getMessage());
            throw new BusinessException("400", "根据id更新卡带详情错误", e);
        }
    }
}
