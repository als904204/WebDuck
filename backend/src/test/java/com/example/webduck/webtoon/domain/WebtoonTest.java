package com.example.webduck.webtoon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("정상적인 경우 예외가 발생하지 않아야 함")
    void validateSizeMismatch() {
        List<Webtoon> actualWebtoons = new ArrayList<>();
        List<Long> webtoonIds = new ArrayList<>();

        for (long i = 1; i <= 10; i++) {
            Webtoon webtoon = Webtoon.builder()
                .id(i)
                .title("title" + i)
                .reviewCount(0)
                .webtoonUrl("url" + i)
                .build();
            actualWebtoons.add(webtoon);
            webtoonIds.add(i);
        }

        assertDoesNotThrow(() -> Webtoon.validateWebtoonIds(actualWebtoons, webtoonIds));
    }

    @Test
    @DisplayName("웹툰 ID 리스트와 실제 웹툰 객체 리스트의 크기 불일치 시 예외 발생")
    void validateSizeMismatchThrown() {
        List<Webtoon> actualWebtoons = new ArrayList<>();
        List<Long> webtoonIds = new ArrayList<>();

        for (long i = 1; i <= 5; i++) {
            Webtoon webtoon = Webtoon.builder()
                .id(i)
                .title("title" + i)
                .reviewCount(0)
                .webtoonUrl("url" + i)
                .build();
            actualWebtoons.add(webtoon);
        }

        // WebtoonIds 리스트에는 추가적인 ID를 더 넣음
        for (long i = 1; i <= 10; i++) {
            webtoonIds.add(i);
        }

        assertThatThrownBy(() -> Webtoon.validateWebtoonIds(actualWebtoons, webtoonIds))
            .isInstanceOf(CustomException.class)
            .hasMessageContaining(LogicExceptionCode.BAD_REQUEST.getMessage());
    }
}