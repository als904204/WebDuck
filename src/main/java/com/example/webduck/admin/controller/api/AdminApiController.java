package com.example.webduck.admin.controller.api;


import com.example.webduck.Webtoon.dto.WebtoonUploadDto;
import com.example.webduck.Webtoon.service.WebtoonService;
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

    private final WebtoonService webtoonService;


    @PostMapping("/webtoon")
    public ResponseEntity<Long> uploadWebtoon(@ModelAttribute WebtoonUploadDto webtoonUploadDto) {
        // DTO 처리 로직
        webtoonService.uploadWebtoon(webtoonUploadDto);
        return null;
    }
}
