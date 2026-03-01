package com.liuao.game_card_sell.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {
    public static final String ORDER_IMMEDIATE_QUEUE = "order_immediate_queue";
    public static final String ORDER_IMMEDIATE_EXCHANGE = "order_immediate_exchange";
    public static final String ORDER_IMMEDIATE_ROUTING_KEY = "order_immediate_routing_key";

    public static final String ORDER_TTL_QUEUE = "order_ttl_queue";
    public static final String ORDER_TTL_EXCHANGE = "order_ttl_exchange";
    public static final String ORDER_TTL_ROUTING_KEY = "order_ttl_routing_key";

    public static final String ORDER_DLX_QUEUE = "order_dlx_queue";
    public static final String ORDER_DLX_EXCHANGE = "order_dlx_exchange";
    public static final String ORDER_DLX_ROUTING_KEY = "order_dlx_routing_key";
    // 将立即创建订单队列绑定到立即创建订单交换机，用对应的立即创建订单路由key
    @Bean
    public Queue orderImmediateQueue() {
        return new Queue(ORDER_IMMEDIATE_QUEUE, true);
    }

    @Bean
    public DirectExchange orderImmediateExchange() {
        return new DirectExchange(ORDER_IMMEDIATE_EXCHANGE);
    }

    @Bean
    public Binding immediateBinding(Queue orderImmediateQueue, DirectExchange orderImmediateExchange) {
        return BindingBuilder.bind(orderImmediateQueue).to(orderImmediateExchange).with(ORDER_IMMEDIATE_ROUTING_KEY);
    }
    // 将TTL队列绑定到TTL交换机，用对应的TTL路由key,配置队列消息过期后转发到死信队列
    @Bean
    public Queue orderTtlQueue() {
        Map<String, Object> args = new HashMap<>();
        // 消息过期后转发到哪个交换机
        args.put("x-dead-letter-exchange", ORDER_DLX_EXCHANGE);
        // 转发时使用的 Routing Key
        args.put("x-dead-letter-routing-key", ORDER_DLX_ROUTING_KEY);
        // 设置队列中所有消息的过期时间（15分钟 = 900000ms）
        args.put("x-message-ttl", 900000);

        return new Queue(ORDER_TTL_QUEUE, true, false, false, args);
    }

    @Bean
    public DirectExchange orderTtlExchange() {
        return new DirectExchange(ORDER_TTL_EXCHANGE);
    }

    @Bean
    public Binding ttlBinding(Queue orderTtlQueue, DirectExchange orderTtlExchange) {
        return BindingBuilder.bind(orderTtlQueue).to(orderTtlExchange).with(ORDER_TTL_ROUTING_KEY);
    }

    // 将死信队列绑定到死信交换机，用对应的死信路由key
    @Bean
    public Queue orderDlxQueue() {
        return new Queue(ORDER_DLX_QUEUE, true);
    }

    @Bean
    public DirectExchange orderDlxExchange() {
        return new DirectExchange(ORDER_DLX_EXCHANGE);
    }

    @Bean
    public Binding dlxBinding(Queue orderDlxQueue, DirectExchange orderDlxExchange) {
        return BindingBuilder.bind(orderDlxQueue).to(orderDlxExchange).with(ORDER_DLX_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}