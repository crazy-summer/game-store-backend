package com.liuao.game_card_sell.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RelativeOrAbsoluteURLValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RelativeOrAbsoluteURL {
    String message() default "必须是有效的URL或相对路径（如 /images/xxx.jpg）";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}