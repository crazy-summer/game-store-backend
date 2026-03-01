package com.liuao.game_card_sell.service;

import com.liuao.game_card_sell.config.RabbitMQConfig;
import com.liuao.game_card_sell.dto.SecKillMessage;
import com.liuao.game_card_sell.dto.request.SecKillRequest;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SecKillService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    private DefaultRedisScript<Long> luaScript;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        luaScript = new DefaultRedisScript<>();
        luaScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("deduct_stock.lua")));
        luaScript.setResultType(Long.class);
    }

    public void seckill(SecKillRequest request) {
        long userId = request.getUserId();
        long cartId = request.getCartId();
        long cartridgeId = request.getCartridgeId();
        // redis预扣减库存,使用lua脚本
        String stockKey = "stock:" + cartridgeId;
        Long result = redisTemplate.execute(luaScript, Collections.singletonList(stockKey));
        // 如果返回-1，代表库存<=0,扣减失败
        // 如果返回1,代表库存扣减成功，立刻发送mq消息，通知扣减db库存
        if (result == 1) {
            SecKillMessage message = new SecKillMessage(userId, cartridgeId);

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.ORDER_IMMEDIATE_EXCHANGE,
                    RabbitMQConfig.ORDER_IMMEDIATE_ROUTING_KEY,
                    message
            );

            System.out.println("用戶 " + userId + " 搶購成功，已進入下單隊列，订单将在15分钟后自动取消（若未支付）...");
        } else {
            // 庫存不足邏輯
            throw new RuntimeException("商品已售完或抢购失败");
        }
    }
}


