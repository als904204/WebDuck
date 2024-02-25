package com.example.webduck.config;

import com.example.webduck.config.security.oauth.resolver.LoginMemberArgumentResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final LoginMemberArgumentResolver loginMemberArgumentResolver;

    @Value("${index.page}")
    private String indexPage;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 사용자의 바탕화면 경로 얻기
        String desktopPath = System.getProperty("user.home") + "/Desktop/img/";

        // 리소스 핸들러 설정
        registry.addResourceHandler("/images/**").addResourceLocations("file:" + desktopPath);
    }
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){
        argumentResolvers.add(loginMemberArgumentResolver);
    }

    // TODO : 현재는 모두 허용, 나중에 제한적 허용으로 변경
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/v1/**")
            .allowedOrigins(indexPage)
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true);
    }
}