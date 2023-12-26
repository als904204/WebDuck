package com.example.webduck.admin.controller.api;


import com.example.webduck.Webtoon.dto.WebtoonUploadDto;
import com.example.webduck.admin.service.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminApiController {

    private final UploadService uploadWebtoon;


    @PostMapping("/webtoon")
    public ResponseEntity<Long> uploadWebtoon(@ModelAttribute WebtoonUploadDto webtoonUploadDto) {
        // DTO 처리 로직
        Long uploadWebtoonId = uploadWebtoon.uploadWebtoon(webtoonUploadDto);
        return ResponseEntity.ok(uploadWebtoonId);
    }
}
