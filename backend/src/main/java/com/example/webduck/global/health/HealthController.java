package com.example.webduck.global.health;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/health")
@RestController
public class HealthController {

    @Value("${logging-module.version}")
    private String version;

    @GetMapping
    public String version() {
        return String.format("version : %s", version);
    }

    @GetMapping("/health")
    public String checkHealth() {
        return "success";
    }
}
