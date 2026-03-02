package com.liuao.game_card_sell;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.liuao.game_card_sell.dto.request.SecKillRequest;
import com.liuao.game_card_sell.entity.Cartridge;
import com.liuao.game_card_sell.entity.User;
import com.liuao.game_card_sell.mapper.UserMapper;
import com.liuao.game_card_sell.service.CartridgeService;
import com.liuao.game_card_sell.service.CustomUserDetailsService;
import com.liuao.game_card_sell.service.SecKillService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.liuao.game_card_sell.config.RabbitMQConfig.*;

@Slf4j
@SpringBootTest
class GameCardSellApplicationTests {
	// 初始化 Faker，建议在类级别初始化一次以提高性能
	private static final Faker faker = new Faker(new Locale("zh-CN"));

	@Autowired
	private CartridgeService cartridgeService;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private SecKillService secKillService;

	@Autowired
	private org.springframework.amqp.core.AmqpAdmin amqpAdmin;

	@Autowired
	private RabbitListenerEndpointRegistry registry;

	@Test
	void contextLoads() {
	}

	@BeforeEach
	void cleanRabbitMQ() {
		// 1. 停止所有异步监听器，防止在清理过程中有消息被消费
		registry.stop();
		System.out.println("⏸ 已暂停所有 RabbitMQ 监听器");

		// 这里的 Queue Name 需对应 RabbitMQConfig 中定义的常量
		amqpAdmin.purgeQueue(ORDER_IMMEDIATE_QUEUE, false);
		amqpAdmin.purgeQueue(ORDER_TTL_QUEUE, false);
		amqpAdmin.purgeQueue(ORDER_DLX_QUEUE, false);
		System.out.println("🧹 已清空 RabbitMQ 历史残留消息");

		// 3. 重新启动监听器，开始新的测试循环
		registry.start();
		System.out.println("▶️ 已重启 RabbitMQ 监听器，队列已排空");
	}

	@BeforeEach
	void cleanRedis() {
		Set<String> keys = redisTemplate.keys("stock:*");
		if (!keys.isEmpty()) {
			redisTemplate.delete(keys);
		}
		keys = redisTemplate.keys("cartridge:detail:*");
		if (!keys.isEmpty()) {
			redisTemplate.delete(keys);
		}
		System.out.println("🧹 已清理 Redis 库存缓存");
	}

	@Test
	void testInsertBatchUser() {
		List<User> users = new ArrayList<>();
		for (int i = 0; i < 10000; i++) {
			User user = new User();
			user.setUsername(String.format("%s_%d", faker.name().username(), i));
			String randomPrefix = faker.internet().emailAddress().split("@")[0];
			user.setEmail(String.format("%s_%d@%s", randomPrefix, i, "example.com"));
			user.setPassword("123456");
			// 生成符合中国格式的手机号 (11位，以1开头)
			user.setPhone(faker.phoneNumber().cellPhone());
			users.add(user);
		}
		customUserDetailsService.saveBatch(users);
	}

	// 10000人并发抢购1000个卡带，验证抢购成功人数==1000，失败人数==9000，redis库存从1000扣减到0
	@Test
	public void testSecKill() throws InterruptedException {
		// 1.准备卡带
		QueryWrapper<Cartridge> queryWrapper = new QueryWrapper<>();
		Cartridge cartridge = cartridgeService.getOne(queryWrapper.eq("title", "塞尔达"));
		if(cartridge == null){
			throw new RuntimeException("cartridge为null");
//			cartridge = new Cartridge();
//			cartridge.setPrice(BigDecimal.valueOf(220));
//			cartridge.setTitle("塞尔达");
		}
		cartridge.setStock(1000);
		cartridgeService.saveOrUpdate(cartridge);

		// 【手动确认】确保数据库里真的有了，这一步也能起到强制刷盘的作用
		long cId = cartridge.getId();
		Cartridge check = cartridgeService.getById(cId);
		if (check == null) throw new RuntimeException("数据库写入失败，ID: " + cId);

		// 【新增】核心步骤：预热 Redis 库存
		redisTemplate.opsForValue().set("stock:" + cId, "1000");
		// 确保 Redis 真的写进去了
		System.out.println("Redis 库存已初始化: " + redisTemplate.opsForValue().get("stock:" + cId));

		// 存详情（用于消费者下单时获取卡带价格等信息），设置过期时间防止常驻内存
		String detailKey = "cartridge:detail:" + cId;
		// 这里可以使用 JSON 序列化，或者简单存一个价格
		redisTemplate.opsForValue().set(detailKey, cartridge.getPrice().toString(), Duration.ofHours(1));

		// 2. 获取10000用户
		List<User> users = customUserDetailsService.list(new QueryWrapper<User>().last("LIMIT 10000"));
		if (users.size() < 10000) throw new RuntimeException("用户不足10000");

		// 3. 并发秒杀

		// 3.1【关键】使用线程安全集合收集失败的原因
		ConcurrentLinkedQueue<String> failureMessages = new ConcurrentLinkedQueue<>();
		ConcurrentLinkedQueue<Long> successUserIds = new ConcurrentLinkedQueue<>();

		int threadCount = 10000;
		CountDownLatch latch = new CountDownLatch(threadCount);
		ExecutorService executor = Executors.newFixedThreadPool(1000);

		for (int i = 0; i < threadCount; i++) {
			final Long userId = users.get(i).getId();
			SecKillRequest request = new SecKillRequest();
			request.setUserId(userId);
			request.setCartridgeId(cId);
			executor.submit(() -> {
				try {
					secKillService.seckill(request);
					// 如果没有抛异常，记录成功
					successUserIds.add(userId);
				} catch (RuntimeException e) {
					// 【关键】捕获特定异常并记录消息，而不是仅仅打印
					if ("商品已售完或抢购失败".equals(e.getMessage())) {
						failureMessages.add("用户 " + userId + ": " + e.getMessage());
					} else {
						// 其他意外异常也记录，方便调试
						failureMessages.add("用户 " + userId + " 意外错误: " + e.getMessage());
						e.printStackTrace();
					}
				}catch (Exception e) {
					System.out.println("用户 " + userId + " 抢购失败: " + e.getMessage());
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();
		executor.shutdown();

		// 等待rabbitmq写入订单
		Thread.sleep(25000);

		// 判断成功抢购用户数量为1000
		Assertions.assertEquals(1000, successUserIds.size(), String.format("预期有1000个用户抢购成功，实际上抢购成功用户数量：%s", successUserIds.size()));

		// A. 断言必须有失败的情况 (因为10000人抢1000个，至少9000个失败)
		Assertions.assertFalse(failureMessages.isEmpty(), "预期至少有部分用户抢购失败，但所有用户都成功了");

		// B. 断言失败的原因确实是“商品已售完”
		long stockOutCount = failureMessages.stream()
				.filter(msg -> msg.contains("商品已售完或抢购失败"))
				.count();

		// 理论上应该有 9000 个失败 (10000 - 1000)，考虑到并发极端情况，至少保证有失败
		Assertions.assertTrue(stockOutCount >= 9000,
				String.format("预期至少9000个用户因库存不足失败，实际只有 %d 个。失败详情: %s",
						stockOutCount, failureMessages.stream().limit(5).collect(Collectors.toList())));

		// C. 断言成功的数量大约是 1000 (允许少量误差，如果Redis脚本严谨则应为900)
		Assertions.assertEquals(1000, successUserIds.size(),
				"预期成功下单的用户数应为900人");

		// 断言redis库存==0
		String redisStockStr = redisTemplate.opsForValue().get("stock:" + cartridge.getId());
		int redisStock = Integer.parseInt(redisStockStr);
		Assertions.assertEquals(0, redisStock, String.format("redis库存应该为0，实际为%s", redisStock));

		System.out.println("✅ 测试通过：成功 " + successUserIds.size() + " 人，失败 " + stockOutCount + " 人 (库存不足)");
	}

}
