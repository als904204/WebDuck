package com.example.webduck.collection.infrastructure.entity;

import com.example.webduck.collection.domain.Collection;
import com.example.webduck.global.common.BaseTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@Entity
public class CollectionEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Length(min = 2, max = 18)
    @Column(nullable = false, length = 16)
    private String title;

    @Column(nullable = false, length = 255)
    private String description;

    private boolean isPublic;

    @Column(nullable = false)
    private Long ownerId;

    @Column(nullable = false)
    private String ownerName;

    protected CollectionEntity() {}

    @Builder
    public CollectionEntity(String title, String description, boolean isPublic, Long ownerId,
        String ownerName) {
        this.title = title;
        this.description = description;
        this.isPublic = isPublic;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
    }

    public static CollectionEntity from(Collection collection) {
        return CollectionEntity.builder()
            .title(collection.getTitle())
            .description(collection.getDescription())
            .isPublic(collection.isPublic())
            .ownerId(collection.getOwnerId())
            .ownerName(collection.getOwnerName())
            .build();
    }

    public Collection toModel() {
        return Collection.builder()
            .id(id)
            .title(title)
            .description(description)
            .isPublic(isPublic)
            .ownerId(ownerId)
            .build();
    }
}
