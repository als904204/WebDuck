package com.example.webduck.webtoon.dto;

import com.example.webduck.webtoon.entity.Platform;
import com.example.webduck.webtoon.entity.PublishDay;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class WebtoonUpload {
    private String title;
    private String summary;
    private PublishDay publishDay;
    private MultipartFile imageFile;
    private Platform platform;
}

