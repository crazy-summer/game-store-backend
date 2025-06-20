package com.liuao.game_card_sell;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.liuao.game_card_sell.mapper")  // 确保包路径正确
public class GameCardSellApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameCardSellApplication.class, args);
	}

}
