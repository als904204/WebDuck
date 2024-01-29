package com.example.webduck.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminController {


    @GetMapping
    public String adminPage() {
        return "/admin/adminPage";
    }

    @GetMapping("/webtoon")
    public String adminWebtoonPage() {
        return "/admin/webtoon/adminWebtoonMain";
    }

    @GetMapping("/webtoon/upload")
    public String adminWebtoonUpload() {
        return "/admin/webtoon/uploadWebtoon";
    }


}
