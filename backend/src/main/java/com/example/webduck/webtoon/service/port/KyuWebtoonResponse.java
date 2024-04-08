package com.example.webduck.webtoon.service.port;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class KyuWebtoonResponse {

    private Result result;
    private List<WebtoonKyu> itemList;

    public boolean isSuccess() {
        return result.getResultState().equals("success");
    }

    public boolean isEmpty() {
        return itemList.isEmpty();
    }
    public int getTotalCount() {
        return result.getTotalCount();
    }

    public String getResultMessage() {
        return result.getResultMessage();
    }

    public String getResultState() {
        return result.getResultState();
    }

    @Getter
    public static class Result {
        private int viewItemCnt;
        private int pageNo;
        private String resultState;
        private int totalCount;
        private String resultMessage;
    }
    @AllArgsConstructor
    @Getter
    public static class WebtoonKyu {
        private String pictrWritrNm;
        private String sntncWritrNm;
        private String outline;
        private String mainGenreCdNm;
        private String prdctNm;
    }


}
