package com.example.webduck.review.dto;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ReviewSave {

    @NotNull(message = "webtoonId는 null이 될 수 없습니다")
    private final Long webtoonId;

    @NotBlank(message = "리뷰는 빈칸이 될 수 없습니다")
    @Size(max = 255,message = "리뷰는 최대 255자까지 작성할 수 있습니다")
    private final String content;

    @NotNull(message = "rating은 빈값이 될 수 없습니다")
    @Min(value = 1, message = "rating은 최소 1점 이상이어야 합니다")
    @Max(value = 5, message = "rating은 최대 5점까지 가능합니다")
    private final Integer rating;


    public ReviewSave(Long webtoonId, String content, int rating) {
        this.webtoonId = webtoonId;
        this.content = content;
        this.rating = rating;
    }

    public Long getWebtoonId() {
        return webtoonId;
    }

    public String getContent() {
        return content;
    }
    public int getRating() {
        return rating;
    }
}
