package com.example.webduck.Webtoon.controller.api;

import com.example.webduck.Webtoon.dto.WebtoonRequest;
import com.example.webduck.Webtoon.entity.PublishDay;
import com.example.webduck.Webtoon.service.WebtoonService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/webtoon")
@RestController
public class WebtoonApiController {

    private final WebtoonService webtoonService;

    @GetMapping("/{id}")
    public ResponseEntity<WebtoonRequest> getWebtoon(@PathVariable Long id) {
        WebtoonRequest request = webtoonService.findWebtoonById(id);
        return ResponseEntity.ok(request);
    }

    @GetMapping
    public ResponseEntity<List<WebtoonRequest>> getWebtoonList() {
        List<WebtoonRequest> webtoonList = webtoonService.findWebtoonList();
        return ResponseEntity.ok(webtoonList);
    }

    @GetMapping("/publish")
    public ResponseEntity<List<WebtoonRequest>> getWebtoonListByPublish(@RequestParam("publishDay") PublishDay publishDay) {
        List<WebtoonRequest> webtoonList = webtoonService.findWebtoonByPublishDay(publishDay);
        return ResponseEntity.ok(webtoonList);
    }

}
