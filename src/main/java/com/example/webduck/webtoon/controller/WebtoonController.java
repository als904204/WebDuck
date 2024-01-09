package com.example.webduck.webtoon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebtoonController {

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


}
