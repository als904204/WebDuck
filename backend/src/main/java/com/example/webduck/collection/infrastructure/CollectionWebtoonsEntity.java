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

    @Column(name = "webtoon_id")
    private Long webtoonId;

    @Builder
    public CollectionWebtoonsEntity(Long id, Long webtoonId, Long ownerId) {
        this.id = id;
        this.webtoonId = webtoonId;
        this.ownerId = ownerId;
    }

    protected CollectionWebtoonsEntity() {
    }

    public static CollectionWebtoonsEntity from(CollectionWebtoons collectionWebtoons) {
        return CollectionWebtoonsEntity.builder()
            .id(collectionWebtoons.getId())
            .ownerId(collectionWebtoons.getOwnerId())
            .webtoonId(collectionWebtoons.getWebtoonId())
            .build();
    }

    public CollectionWebtoons toModel() {
        return CollectionWebtoons.builder()
            .id(id)
            .ownerId(ownerId)
            .webtoonId(webtoonId)
            .build();
    }
}
