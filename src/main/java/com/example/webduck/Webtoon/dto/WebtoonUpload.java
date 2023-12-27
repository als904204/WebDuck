package com.example.webduck.Webtoon.dto;

import com.example.webduck.Webtoon.entity.PublishDay;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class WebtoonUpload {
    private String title;
    private String summary;
    private PublishDay publishDay;
    private MultipartFile imageFile;
}

