package com.example.webduck.admin.controller.response;


import com.example.webduck.webtoon.infrastructure.Platform;
import lombok.Getter;

@Getter
public class WebtoonMergeResponse {

    private String platform;
    private int count;

    public WebtoonMergeResponse(int count, Platform platform) {
        this.count = count;
        this.platform = platform.name();
    }
}
