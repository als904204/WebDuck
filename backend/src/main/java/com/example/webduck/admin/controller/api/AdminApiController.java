package com.example.webduck.admin.controller.api;


import com.example.webduck.webtoon.dto.WebtoonUpload;
import com.example.webduck.admin.service.UploadService;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminApiController {

    private final UploadService uploadService;

    @PostMapping("/webtoon")
    public ResponseEntity<Long> uploadWebtoon(@Valid @ModelAttribute WebtoonUpload webtoonUpload) {
        uploadService.uploadWebtoon(webtoonUpload);
        return ResponseEntity.noContent().build();
    }
}
