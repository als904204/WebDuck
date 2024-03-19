package com.example.webduck.collection.infrastructure;

import com.example.webduck.collection.domain.CollectionWebtoons;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;

@Table(name = "collection_webtoons")
@Entity
public class CollectionWebtoonsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ownerId")
    private Long ownerId;

    // todo 꼭 필요한가?
    @Column(name = "webtoon_id")
    private Long webtoonId;

    @Column(name = "collection_id")
    private Long collectionId;

    @Builder
    public CollectionWebtoonsEntity(Long id, Long webtoonId, Long ownerId, Long collectionId) {
        this.id = id;
        this.webtoonId = webtoonId;
        this.ownerId = ownerId;
        this.collectionId = collectionId;
    }

    protected CollectionWebtoonsEntity() {
    }

    public static CollectionWebtoonsEntity from(CollectionWebtoons collectionWebtoons) {
        return CollectionWebtoonsEntity.builder()
            .id(collectionWebtoons.getId())
            .ownerId(collectionWebtoons.getOwnerId())
            .webtoonId(collectionWebtoons.getWebtoonId())
            .collectionId(collectionWebtoons.getCollectionId())
            .build();
    }

    public CollectionWebtoons toModel() {
        return CollectionWebtoons.builder()
            .id(id)
            .ownerId(ownerId)
            .webtoonId(webtoonId)
            .collectionId(collectionId)
            .build();
    }
}
