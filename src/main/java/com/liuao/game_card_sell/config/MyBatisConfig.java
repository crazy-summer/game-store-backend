package com.liuao.game_card_sell.config;

import com.liuao.game_card_sell.filter.mybatis.CreateTimeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {
    @Bean
    public CreateTimeInterceptor createTimeInterceptor() {
        return new CreateTimeInterceptor();
    }
}