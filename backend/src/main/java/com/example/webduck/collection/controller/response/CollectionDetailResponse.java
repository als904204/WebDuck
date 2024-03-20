package com.example.webduck.collection.controller.response;

import com.example.webduck.collection.domain.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CollectionDetailResponse {

    private Long id;
    private String title;
    private String description;
    private List<WebtoonInfo> webtoonInfo;

    public static CollectionDetailResponse from(Collection collection,
        List<WebtoonInfo> webtoonInfo) {
        return CollectionDetailResponse.builder()
            .id(collection.getId())
            .title(collection.getTitle())
            .description(collection.getDescription())
            .webtoonInfo(webtoonInfo)
            .build();
    }

    @AllArgsConstructor
    @Getter
    public static class WebtoonInfo {
        private Long id;
        private String title;
    }
}
