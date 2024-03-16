package com.example.webduck.collection.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CollectionWebtoons {

    private Long id;

    private Long ownerId;

    private Long webtoonId;

    @Builder
    public CollectionWebtoons(Long id, Long ownerId, Long webtoonId) {
        this.id = id;
        this.ownerId = ownerId;
        this.webtoonId = webtoonId;
    }

    public static CollectionWebtoons from(CollectionWebtoons collectionWebtoons) {
        return CollectionWebtoons.builder()
            .id(collectionWebtoons.id)
            .ownerId(collectionWebtoons.ownerId)
            .webtoonId(collectionWebtoons.webtoonId)
            .build();
    }

}
