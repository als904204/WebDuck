package com.example.webduck.global.exception.exceptionCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum LogicExceptionCode implements ExceptionCode {
    BAD_REQUEST(400, "Bad Request");

    @Getter
    private int status;

    @Getter
    private String message;
}
