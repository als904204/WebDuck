package com.example.webduck.global.exception.exceptionCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum LogicExceptionCode implements ExceptionCode {
    BAD_REQUEST(400, "Bad Request"),
    WEBTOON_NOT_FOUND(404, "Webtoon not found"),
    DAY_NOT_FOUND(404, "Publish day not found"),
    REVIEW_NOT_FOUND(404, "Review not found"),
    COLLECTION_NOT_FOUND(404, "Collection not found"),
    MEMBER_NOT_FOUND(404, "Member not found"),
    DUPLICATE_REQUEST(409,"Duplicate request"),
    RANGE_NOT_SATISFIABLE(416, "No more data can be provided on the API Server"),
    MAXIMUM_TRAFFIC(400,"Maximum traffic exceeded from Kyu API Server"),
    ENCRYPTOR_SECRET_KEY(500,"AttributeEncryptor SECRET_KEY config error"),
    SERVER_ERROR(500,"SERVER ERROR please check your log");


    @Getter
    private int status;

    @Getter
    private String message;
}
