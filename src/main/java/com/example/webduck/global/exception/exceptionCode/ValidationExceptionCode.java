package com.example.webduck.global.exception.exceptionCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ValidationExceptionCode implements ExceptionCode {

    INVALID_LOCATION(400, "Invalid location value"),
    INVALID_STATE(400, "Invalid state value"),
    INVALID_OAUTH_TYPE(400, "Invalid oauth type"),

    // 지원하지 않는 파일 확장자를 업로드 했을 때
    INVALID_FILE_TYPE(415, "Invalid file type");

    @Getter
    private int status;

    @Getter
    private String message;
}
