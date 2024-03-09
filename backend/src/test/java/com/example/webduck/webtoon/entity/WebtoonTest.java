package com.example.webduck.webtoon.entity;

import static org.assertj.core.api.Assertions.*;

import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import org.junit.jupiter.api.Test;

class WebtoonTest {

    @Test
    void validateSizeMismatch_success() {
        int actual = 5;
        int expected = 5;

        Webtoon.validateSizeMismatch(actual, expected);
        assertThatNoException()
            .isThrownBy(() -> Webtoon.validateSizeMismatch(actual, expected));
    }

    @Test
    void validateSizeMismatch_exception() {
        int actual = 3;
        int expected = 5;

        // Then
        assertThatThrownBy(() -> Webtoon.validateSizeMismatch(actual, expected))
            .isInstanceOf(CustomException.class)
            .hasMessageContaining(LogicExceptionCode.BAD_REQUEST.getMessage());
    }
}