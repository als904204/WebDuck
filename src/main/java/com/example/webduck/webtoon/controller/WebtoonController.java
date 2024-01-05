package com.example.webduck.webtoon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebtoonController {

    @GetMapping("/publish")
    public String dailyPage() {
        return "views/publish";
    }


}
