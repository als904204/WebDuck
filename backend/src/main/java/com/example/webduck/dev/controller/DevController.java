package com.example.webduck.dev.controller;

import com.example.webduck.dev.service.DevService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/dev")
@Profile("dev")
public class DevController {

    private final DevService devService;

    @GetMapping("/login")
    public ResponseEntity<Void> devAdminLogin() {
        devService.login();
        return ResponseEntity.noContent().build();

    }
}
