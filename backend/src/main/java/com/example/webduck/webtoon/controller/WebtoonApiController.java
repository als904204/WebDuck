package com.example.webduck.webtoon.controller;

import com.example.webduck.webtoon.controller.port.WebtoonService;
import com.example.webduck.webtoon.controller.response.WebtoonDetails;
import com.example.webduck.webtoon.controller.response.WebtoonGenreResponse;
import com.example.webduck.webtoon.controller.response.WebtoonPopularResponse;
import com.example.webduck.webtoon.controller.response.WebtoonResponse;
import com.example.webduck.webtoon.infrastructure.Platform;
import com.example.webduck.webtoon.infrastructure.PublishDay;
import com.example.webduck.webtoon.infrastructure.WebtoonSortCondition;
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
    public ResponseEntity<WebtoonDetails> getWebtoonDetailsById(@PathVariable Long id) {
        WebtoonDetails webtoonDetails = webtoonService.getWebtoonDetails(id);
        return ResponseEntity.ok(webtoonDetails);
    }

    @GetMapping
    public ResponseEntity<List<WebtoonResponse>> findWebtoonList() {
        List<WebtoonResponse> response = webtoonService.findAll();
        return ResponseEntity.ok(response);
    }

    // 요일별 웹툰 목록 조회 (MONDAY,SUNDAY..)
    @GetMapping("/publish")
    public ResponseEntity<List<WebtoonResponse>> featWebtoonListByPublish(@RequestParam("day") PublishDay publishDay) {
        List<WebtoonResponse> webtoonList = webtoonService.findWebtoonsByPublishDay(publishDay);
        return ResponseEntity.ok(webtoonList);
    }

    // 요청에 따른 플랫폼 별 웹툰 목록 조회 (NAVER,KAKAO..)
    @GetMapping("/platform")
    public ResponseEntity<List<WebtoonResponse>> findWebtoonListByPlatform(@RequestParam("type") Platform platform) {
        List<WebtoonResponse> webtoonList = webtoonService.findWebtoonsByPlatform(platform);
        return ResponseEntity.ok(webtoonList);
    }

    // 장르 요청에 따른 웹툰 목록 조회 (ex : 무협,개그,판타지 장르를 포함한 웹툰)
    @GetMapping("/genres")
    public ResponseEntity<List<WebtoonGenreResponse>> getWebtoonListByGenreNames(@RequestParam("names") List<String> names) {
        List<WebtoonGenreResponse> webtoonsByGenreNames = webtoonService.findWebtoonsByGenreNames(
            names);
        return ResponseEntity.ok(webtoonsByGenreNames);
    }

    // 인기별 웹툰 조회 (리뷰 개수 순, 평점 순)
    @GetMapping("/popular")
    public ResponseEntity<List<WebtoonPopularResponse>> findPopularWebtoonListByCondition(
        @RequestParam WebtoonSortCondition condition) {
        List<WebtoonPopularResponse> webtoonsByWebtoonCondition = webtoonService.findPopularWebtoonsByCondition(
            condition);
        return ResponseEntity.ok(webtoonsByWebtoonCondition);
    }

}
