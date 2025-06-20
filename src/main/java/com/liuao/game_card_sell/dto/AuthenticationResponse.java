package com.liuao.game_card_sell.dto;

import com.liuao.game_card_sell.entity.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor // 生成全参构造器
public class AuthenticationResponse {
    private String jwt;

    private UserDto user;
}
