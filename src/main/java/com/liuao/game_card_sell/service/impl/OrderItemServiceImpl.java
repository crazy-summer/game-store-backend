package com.liuao.game_card_sell.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuao.game_card_sell.entity.OrderItem;
import com.liuao.game_card_sell.mapper.OrderItemMapper;
import com.liuao.game_card_sell.service.OrderItemService;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {
}
