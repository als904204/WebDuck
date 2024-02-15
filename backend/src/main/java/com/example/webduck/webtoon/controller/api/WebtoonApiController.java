package com.example.webduck.webtoon.controller.api;

import com.example.webduck.webtoon.dto.WebtoonDetails;
import com.example.webduck.webtoon.dto.WebtoonGenreResponse;
import com.example.webduck.webtoon.dto.WebtoonResponse;
import com.example.webduck.webtoon.dto.WebtoonSortCondition.WebtoonConditionResponse;
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
    public ResponseEntity<WebtoonDetails> getWebtoonDetailsById(@PathVariable Long id) {
        WebtoonDetails webtoonDetails = webtoonService.getWebtoonDetails(id);
        return ResponseEntity.ok(webtoonDetails);
    }

    @GetMapping
    public ResponseEntity<List<WebtoonResponse>> getWebtoonList() {
        List<WebtoonResponse> webtoonList = webtoonService.findWebtoonList();
        return ResponseEntity.ok(webtoonList);
    }

    @GetMapping("/publish")
    public ResponseEntity<List<WebtoonResponse>> getWebtoonListByPublish(@RequestParam("day") PublishDay publishDay) {
        List<WebtoonResponse> webtoonList = webtoonService.findWebtoonsByPublishDay(publishDay);
        return ResponseEntity.ok(webtoonList);
    }

    @GetMapping("/platform")
    public ResponseEntity<List<WebtoonResponse>> getWebtoonListByPlatform(@RequestParam("type") Platform platform) {
        List<WebtoonResponse> webtoonList = webtoonService.findWebtoonsByPlatform(platform);
        return ResponseEntity.ok(webtoonList);
    }

    @GetMapping("/genre")
    public ResponseEntity<List<WebtoonResponse>> getWebtoonListByGenreName(@RequestParam("name") String name) {
        List<WebtoonResponse> webtoonList = webtoonService.findWebtoonsByGenreName(name);
        return ResponseEntity.ok(webtoonList);
    }

    // TODO : 아무런 값 없을경우 모든 웹툰목록 return
    @GetMapping("/genres")
    public ResponseEntity<List<WebtoonGenreResponse>> getWebtoonListByGenreNames(@RequestParam("names") List<String> names) {
        List<WebtoonGenreResponse> webtoonsByGenreNames = webtoonService.findWebtoonsByGenreNames(
            names);
        return ResponseEntity.ok(webtoonsByGenreNames);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<WebtoonConditionResponse>> getPopularWebtoonListByCondition(
        @RequestParam String sortBy) {
        List<WebtoonConditionResponse> webtoonsByWebtoonCondition = webtoonService.findWebtoonsByWebtoonCondition(
            sortBy);
        return ResponseEntity.ok(webtoonsByWebtoonCondition);
    }

}
