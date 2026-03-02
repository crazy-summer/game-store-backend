package com.liuao.game_card_sell.consumer;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuao.game_card_sell.config.RabbitMQConfig;
import com.liuao.game_card_sell.dto.SecKillMessage;
import com.liuao.game_card_sell.entity.Cartridge;
import com.liuao.game_card_sell.entity.Order;
import com.liuao.game_card_sell.entity.OrderItem;
import com.liuao.game_card_sell.mapper.CartridgeMapper;
import com.liuao.game_card_sell.service.CartridgeService;
import com.liuao.game_card_sell.service.OrderItemService;
import com.liuao.game_card_sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigDecimal;

@Slf4j
@Component
public class SecKillOrderConsumer {
    @Autowired
    private CartridgeService cartridgeService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CartridgeMapper cartridgeMapper;

    @Transactional
    @RabbitListener(queues = RabbitMQConfig.ORDER_IMMEDIATE_QUEUE)
    public void handleCreateOrder(SecKillMessage message) {
        long userId = message.getUserId();
        long cId = message.getCartridgeId();

        // 1. 尝试从 Redis 获取价格
        String priceStr = (String) redisTemplate.opsForValue().get("cartridge:detail:" + cId);
        BigDecimal price;

        if (priceStr != null) {
            price = new BigDecimal(priceStr);
        } else {
            log.warn("Redis未命中价格缓存，尝试查库兜底，ID: {}", cId);
            // 兜底：只查价格，不查复杂的关联详情
            price = cartridgeMapper.selectPriceById(cId);
            if (price == null) {
                throw new RuntimeException("数据库也查不到价格，必须重试");
            }
        }

        // 2. 创建order和orderItem
        Order order = new Order();
        order.setUserId(message.getUserId());
        order.setTotalPrice(price);
        orderService.save(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getId());
        orderItem.setCartridgeId(cId);
        orderItem.setQuantity(1);
        orderItem.setPrice(price);
        orderItemService.save(orderItem);

        // 3. 发送消息到TTL队列，15min后转发到死信队列，让其判断订单状态
        // 使用事務同步器：確保 DB Commit 成功後才發送 TTL 延時消息
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                rabbitTemplate.convertAndSend(
                        RabbitMQConfig.ORDER_TTL_EXCHANGE,
                        RabbitMQConfig.ORDER_TTL_ROUTING_KEY,
                        message
                );
            }
        });
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_DLX_QUEUE)
    public void handleDL(SecKillMessage message) {
        long userId = message.getUserId();
        long cartridgeId = message.getCartridgeId();
        // 根据cartridgeId查出orderItem，然后查出orderId
        QueryWrapper<OrderItem> wrapper = new QueryWrapper<>();
        wrapper.eq("cartridge_id", cartridgeId);
        OrderItem one = orderItemService.getOne(wrapper);
        long orderId = one.getOrderId();

        // 根据orderId查出唯一订单，判断订单状态，如果已经支付，那么不管，db库存已经被扣减了，如果没有支付，那么恢复redis库存
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        Order order = orderService.getOne(orderQueryWrapper.eq("id", orderId));
        if (order == null) {
            if(order.getStatus() == 0) {
                // 恢复redis库存
                redisTemplate.opsForValue().increment("stock:"+cartridgeId, 1);
                System.out.println(String.format("用户未支付,订单id: %s, 卡带id: %s", orderId, cartridgeId));
            }else if(order.getStatus() == 1) {
                System.out.println("用户已支付,orderId:"+orderId);
            }
            else{
                System.out.println("用户已确认,orderId:"+orderId);
            }
        }else {
            throw new RuntimeException("订单不存在");
        }

    }
}
