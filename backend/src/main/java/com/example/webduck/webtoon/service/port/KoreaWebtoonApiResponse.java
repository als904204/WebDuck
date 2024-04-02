package com.example.webduck.webtoon.service.port;

import java.util.List;
import lombok.Getter;

@Getter
public class KoreaWebtoonApiResponse {
    private List<WebtoonKorItem> webtoons;
    @Getter
    public static class WebtoonKorItem {
        private String title;
        private String img;
        private List<String> updateDays;
        private String service;
        private String url;

        public String getPublishDay() {
            return updateDays.get(0);
        }

    }
}