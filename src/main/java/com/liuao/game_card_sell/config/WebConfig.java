package com.liuao.game_card_sell.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.access-path}")
    private String accessPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将/images/**映射到文件系统的uploads目录
        Path uploadPath = Paths.get(System.getProperty("user.dir"), uploadDir);
        registry.addResourceHandler(accessPath)
                .addResourceLocations("file:" + uploadPath + "/")
                .setCachePeriod(3600);
    }
}