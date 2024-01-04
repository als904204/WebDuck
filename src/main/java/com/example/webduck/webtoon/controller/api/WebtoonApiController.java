package com.example.webduck.webtoon.controller.api;

import com.example.webduck.webtoon.dto.WebtoonRequest;
import com.example.webduck.webtoon.entity.Platform;
import com.example.webduck.webtoon.entity.PublishDay;
import com.example.webduck.webtoon.service.WebtoonService;
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
        List<WebtoonRequest> webtoonList = webtoonService.findWebtoonsByPublishDay(publishDay);
        return ResponseEntity.ok(webtoonList);
    }

    @GetMapping("/platform")
    public ResponseEntity<List<WebtoonRequest>> getWebtoonListByPlatform(@RequestParam("platform") Platform platform) {
        List<WebtoonRequest> webtoonList = webtoonService.findWebtoonsByPlatform(platform);
        return ResponseEntity.ok(webtoonList);
    }

    @GetMapping("/genre")
    public ResponseEntity<List<WebtoonRequest>> getWebtoonListByGenreName(@RequestParam("name") String name) {
        List<WebtoonRequest> webtoonList = webtoonService.findWebtoonsByGenreName(name);
        return ResponseEntity.ok(webtoonList);
    }

}
