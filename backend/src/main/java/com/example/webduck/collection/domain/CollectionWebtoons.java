package com.example.webduck.collection.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CollectionWebtoons {

    private Long id;
    private Long ownerId;
    private Long webtoonId;
    private Long collectionId;

    @Builder
    public CollectionWebtoons(Long id, Long ownerId, Long webtoonId,Long collectionId) {
        this.id = id;
        this.ownerId = ownerId;
        this.webtoonId = webtoonId;
        this.collectionId = collectionId;
    }

    public static CollectionWebtoons from(CollectionWebtoons collectionWebtoons) {
        return CollectionWebtoons.builder()
            .id(collectionWebtoons.id)
            .ownerId(collectionWebtoons.ownerId)
            .webtoonId(collectionWebtoons.webtoonId)
            .collectionId(collectionWebtoons.collectionId)
            .build();
    }

}
