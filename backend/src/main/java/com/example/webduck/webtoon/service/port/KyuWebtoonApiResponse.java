package com.example.webduck.webtoon.service.port;

import java.util.List;
import lombok.Getter;

@Getter
public class KyuWebtoonApiResponse {

    private Result result;
    private List<WebtoonKyuItem> itemList;

    public boolean isSuccess() {
        return result.getResultState().equals("success");
    }

    public boolean isEmpty() {
        return itemList.isEmpty();
    }
    public int getTotalCount() {
        return result.getTotalCount();
    }

    @Getter
    public static class Result {
        private int viewItemCnt;
        private int pageNo;
        private String resultState;
        private int totalCount;
    }

    @Getter
    public static class WebtoonKyuItem {
        private String pictrWritrNm;
        private String sntncWritrNm;
        private String outline;
        private String mainGenreCdNm;
        private String prdctNm;
    }

}
