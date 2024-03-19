package com.example.webduck.global.exception.exceptionCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum LogicExceptionCode implements ExceptionCode {
    BAD_REQUEST(400, "Bad Request"),
    WEBTOON_NOT_FOUND(404, "Webtoon not found"),
    REVIEW_NOT_FOUND(404, "Review not found"),
    COLLECTION_NOT_FOUND(404, "Collection not found"),
    MEMBER_NOT_FOUND(404, "Member not found"),
    DUPLICATE_REQUEST(409,"Duplicate request" );


    @Getter
    private int status;

    @Getter
    private String message;
}
