package com.example.webduck.global.health;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/health")
@RestController
public class HealthController {

    @Value("${logging-module.version}")
    private String version;

    @GetMapping
    public ResponseEntity<Map<String, String>> version() {
        Map<String, String> map = new HashMap<>();
        map.put("version", version);
        return ResponseEntity.ok().body(map);
    }

}
