package com.example.webduck.webtoon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.example.webduck.global.exception.CustomException;
import org.junit.jupiter.api.Test;

class WebtoonTest {

    @Test
    void incrementReviewCount() {
        Webtoon webtoon = Webtoon.builder()
            .id(1L)
            .title("title")
            .reviewCount(0)
            .webtoonUrl("url")
            .build();

        webtoon.incrementReviewCount();

        assertThat(webtoon.getReviewCount()).isEqualTo(1);
    }

    @Test
    void validateSizeMismatch() {
        assertDoesNotThrow(()->Webtoon.validateSizeMismatch(1, 1));
    }

    @Test
    void validateSizeMismatchThrown() {
        assertThatThrownBy(() ->
            Webtoon.validateSizeMismatch(123, 1999))
            .isInstanceOf(CustomException.class);
    }
}