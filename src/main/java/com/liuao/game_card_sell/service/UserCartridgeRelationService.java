package com.liuao.game_card_sell.service;

import com.liuao.game_card_sell.entity.UserCartridgeRelation;
import com.liuao.game_card_sell.entity.UserCartridgeRelationRequest;
import com.liuao.game_card_sell.exception.BusinessException;
import com.liuao.game_card_sell.mapper.UserCartridgeRelationMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserCartridgeRelationService {
    @Resource
    private UserCartridgeRelationMapper userCartridgeRelationMapper;

    public Boolean updateUserCartridgeRelation(UserCartridgeRelationRequest request) {
        try{
            return userCartridgeRelationMapper.upsert(request) > 0;
        }catch (Exception e){
            log.error("[更新用户卡带关系错误]", e);
            throw new BusinessException("400", "更新用户卡带关系错误", e);
        }
    }

    public UserCartridgeRelation selectUserCartridgeRelation(Long userId, Long cartridgeId) {
        try{
            return userCartridgeRelationMapper.selectUserCartridgeRelation(userId, cartridgeId);
        }catch (Exception e){
            log.error("[查询用户卡带关系错误]", e);
            throw new BusinessException("400", "查询用户卡带关系错误", e);
        }
    }
}
