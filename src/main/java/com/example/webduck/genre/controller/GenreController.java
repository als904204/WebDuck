package com.example.webduck.genre.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GenreController {

    @GetMapping("/genre")
    public String genreWebtoonPage() {
        return "/views/genre";
    }
}
