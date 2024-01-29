package com.example.webduck.webtoon.controller;

import com.example.webduck.webtoon.dto.WebtoonDetails;
import com.example.webduck.webtoon.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class WebtoonController {

    private final WebtoonService webtoonService;


    @GetMapping("/publish")
    public String dailyPage() {
        return "views/publish";
    }

    @GetMapping("/genre")
    public String genreWebtoonPage() {
        return "/views/genre";
    }

    @GetMapping("/naver")
    public String naverWebtoonPage() {
        return "/views/naver";
    }

    @GetMapping("/kakao")
    public String kakaoWebtoonPage() {
        return "/views/kakao";
    }

    @GetMapping("/webtoon/details/{id}")
    public String webtoonDetails(Model model, @PathVariable("id") Long id) {
        WebtoonDetails webtoonDetails = webtoonService.getWebtoonDetails(id);
        model.addAttribute("webtoon", webtoonDetails);
        return "views/webtoonDetails";
    }
}
