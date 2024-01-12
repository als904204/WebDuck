package com.example.webduck.review.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ReviewRequest {

    @NotNull(message = "webtoonId는 null이 될 수 없습니다")
    private final Long webtoonId;

    @NotBlank(message = "리뷰는 빈칸이 될 수 없습니다")
    @Size(max = 255,message = "리뷰는 최대 255자까지 작성할 수 있습니다")
    private final String content;


    public ReviewRequest(Long webtoonId, String content) {
        this.webtoonId = webtoonId;
        this.content = content;
    }

    public Long getWebtoonId() {
        return webtoonId;
    }

    public String getContent() {
        return content;
    }
}
