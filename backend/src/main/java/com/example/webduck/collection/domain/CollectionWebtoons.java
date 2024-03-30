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

    public static CollectionWebtoons from(Long memberId,Long webtoonId,Long collectionId) {
        return CollectionWebtoons.builder()
            .ownerId(memberId)
            .webtoonId(webtoonId)
            .collectionId(collectionId)
            .build();
    }
}
