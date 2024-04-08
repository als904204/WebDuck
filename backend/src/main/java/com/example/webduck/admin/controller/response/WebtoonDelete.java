package com.example.webduck.admin.controller.response;

public class WebtoonDelete {

    private String message;
    private long count;

    public WebtoonDelete(String message, long count) {
        this.message = message;
        this.count = count;
    }

}
