package com.example.webduck.global.config;

import com.example.webduck.global.handler.MemoryMonitorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {


    private final MemoryMonitorHandler memoryMonitorHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(memoryMonitorHandler, "/api/v1/admin/memory")
            .setAllowedOrigins("*");

    }

}
