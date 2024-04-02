package com.example.webduck.admin.controller;


import com.example.webduck.webtoon.domain.WebtoonUpload;
import com.example.webduck.admin.service.UploadService;

import com.example.webduck.webtoon.infrastructure.Platform;
import com.example.webduck.webtoon.service.WebtoonApiClient;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminApiController {

    private final UploadService uploadService;
    private final WebtoonApiClient webtoonApiClient;
    @PostMapping("/webtoon")
    public ResponseEntity<Long> uploadWebtoon(@Valid @ModelAttribute WebtoonUpload webtoonUpload) {
        uploadService.uploadWebtoon(webtoonUpload);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/kor/init")
    public ResponseEntity<String> initKorApiWebtoons(@RequestParam Platform platform) {
        int size = webtoonApiClient.saveKorApiByPlatform(platform);
        return ResponseEntity.ok("Korea Webtoon data has been successfully saved. size=" + size);
    }

    @PostMapping("/kyu/init")
    public ResponseEntity<String> initKyuApiWebtoons(@RequestParam Platform platform) {
        int size = webtoonApiClient.activateMatchedWebtoonsWithKyuApi(platform);
        return ResponseEntity.ok(
            "Kyu Webtoon data has been successfully synced and saved. size=" + size);
    }

}
