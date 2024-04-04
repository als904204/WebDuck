package com.example.webduck.webtoon.service.port;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class KoreaWebtoonResponse {
    private List<WebtoonKor> webtoons;


    @AllArgsConstructor
    @Getter
    public static class WebtoonKor {
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
