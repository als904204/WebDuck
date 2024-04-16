package com.example.webduck.webtoon.service.port;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class KoreaWebtoonResponse {
    private List<WebtoonKor> webtoons;

    @Setter
    @NoArgsConstructor
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
