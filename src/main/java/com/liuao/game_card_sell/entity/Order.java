package com.liuao.game_card_sell.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("orders")
public class Order {
    @TableId(type = IdType.AUTO) // 2. 指定主键策略为自增 (MySQL AUTO_INCREMENT)
    private Long id;
    private Long userId;
    // 【重要修正】金额必须用 BigDecimal，对应数据库 DECIMAL(10,2)
    private BigDecimal totalPrice;
    private int status;
    // 3. 处理时间字段
    // 选项 A: 让数据库默认值生效 (插入时不传该字段)
    // 选项 B: 使用注解自动填充 (推荐)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
