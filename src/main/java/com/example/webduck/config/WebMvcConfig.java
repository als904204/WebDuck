package com.example.webduck.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 사용자의 바탕화면 경로 얻기
        String desktopPath = System.getProperty("user.home") + "/Desktop/img/";

        // 리소스 핸들러 설정
        registry.addResourceHandler("/images/**").addResourceLocations("file:" + desktopPath);
    }
}