package com.example.webduck.global.exception.exceptionCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum LogicExceptionCode implements ExceptionCode {
    BAD_REQUEST(400, "Bad Request"),
    JSON_REQUEST_FAILED(401, "Json Request Failed.");


    @Getter
    private int status;

    @Getter
    private String message;
}
