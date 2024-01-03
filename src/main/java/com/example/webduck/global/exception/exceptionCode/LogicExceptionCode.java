package com.example.webduck.global.exception.exceptionCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum LogicExceptionCode implements ExceptionCode {
    BAD_REQUEST(400, "Bad Request"),
    WEBTOON_NOT_FOUND(404, "Webtoon not found");

    @Getter
    private int status;

    @Getter
    private String message;
}
