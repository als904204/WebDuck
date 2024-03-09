package com.example.webduck.collection.infrastructure.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;

@Entity
public class CollectionWebtoons {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ownerId")
    private Long ownerId;

    @Column(name = "webtoon_id")
    private Long webtoonId;

    @Builder
    public CollectionWebtoons(Long webtoonId, Long ownerId) {
        this.webtoonId = webtoonId;
        this.ownerId = ownerId;
    }

    protected CollectionWebtoons() {
    }
}
