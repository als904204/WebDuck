package com.example.webduck.review.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ReviewUpdate {

    @NotBlank(message = "리뷰는 빈칸이 될 수 없습니다")
    @Size(max = 255, message = "리뷰는 최대 255자까지 작성할 수 있습니다")
    private final String content;

    public ReviewUpdate(Long reviewId, String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;}

}
