package com.example.webduck.global.exception;

import com.example.webduck.global.exception.exceptionCode.ExceptionCode;
import lombok.Getter;

public class CustomException extends RuntimeException {
    @Getter
    private ExceptionCode exceptionCode;

    public CustomException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}