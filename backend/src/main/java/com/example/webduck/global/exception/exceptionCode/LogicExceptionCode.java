package com.example.webduck.global.exception.exceptionCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum LogicExceptionCode implements ExceptionCode {
    BAD_REQUEST(400, "Bad Request"),
    WEBTOON_NOT_FOUND(404, "Webtoon not found"),
    REVIEW_NOT_FOUND(404, "Review not found"),
    COLLECTION_NOT_FOUND(404, "Collection not found"),
    MEMBER_NOT_FOUND(404, "Member not found"),
    DUPLICATE_REQUEST(409,"Duplicate request"),
    API_REQUEST_ERROR(400,"API Request error! please check log"),
    THREAD_INTERRUPTED_ERROR(HttpStatus.REQUEST_TIMEOUT.value(), "Thread interrupted error"),
    SERVER_ERROR(500,"SERVER ERROR please check your log");


    @Getter
    private int status;

    @Getter
    private String message;
}
