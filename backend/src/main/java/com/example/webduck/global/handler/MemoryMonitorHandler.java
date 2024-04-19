package com.example.webduck.global.handler;

import com.example.webduck.global.util.RuntimeUtil;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class MemoryMonitorHandler extends TextWebSocketHandler {
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private final Gson gson = new Gson();

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session){
        executorService.scheduleAtFixedRate(() -> {
            try {
                session.sendMessage(new TextMessage(getMemoryInfo()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
    private String getMemoryInfo() {
        Map<String, String> memoryInfo = new HashMap<>();
        memoryInfo.put("totalMemory", RuntimeUtil.getTotalMemoryStringInMb());
        memoryInfo.put("usedMemory", RuntimeUtil.getUsedMemoryStringInMb());
        memoryInfo.put("freeMemory", RuntimeUtil.getFreeMemoryStringInMb());
        memoryInfo.put("hostProcessors", RuntimeUtil.getAvailableProcessors());

        return gson.toJson(memoryInfo);
    }

}

