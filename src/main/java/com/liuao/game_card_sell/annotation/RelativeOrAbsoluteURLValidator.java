package com.liuao.game_card_sell.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class RelativeOrAbsoluteURLValidator implements ConstraintValidator<RelativeOrAbsoluteURL, String> {

    @Override
    public boolean isValid(String url, ConstraintValidatorContext context) {
        if (url == null || url.isBlank()) {
            return false; // 由 @NotBlank 处理
        }

        // 检查是否是有效的 HTTP/HTTPS URL
        if (url.startsWith("http://") || url.startsWith("https://")) {
            try {
                new URI(url).toURL();
                return true;
            } catch (URISyntaxException | IllegalArgumentException | MalformedURLException e) {
                return false;
            }
        }

        // 检查是否是有效的相对路径（如 /images/xxx.jpg）
        return url.startsWith("/") && url.length() > 1 && url.contains(".");
    }
}