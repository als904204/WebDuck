package com.example.webduck.collection.infrastructure;

import com.example.webduck.collection.domain.Collection;
import com.example.webduck.global.common.BaseTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Builder
@AllArgsConstructor
@Table(name = "collection")
@Getter
@Entity
public class CollectionEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Length(min = 2, max = 20)
    @Column(nullable = false)
    private String title;

    @Length(min = 2, max = 200)
    @Column(nullable = false)
    private String description;


    @Column(nullable = false)
    private Long ownerId;

    @Column(nullable = false)
    private String ownerName;

    private boolean isOwner;

    protected CollectionEntity() {}


    public static CollectionEntity from(Collection collection) {
        return CollectionEntity.builder()
            .id(collection.getId())
            .title(collection.getTitle())
            .description(collection.getDescription())
            .ownerId(collection.getOwnerId())
            .ownerName(collection.getOwnerName())
            .isOwner(collection.isOwner())
            .build();
    }

    public Collection toModel() {
        return Collection.builder()
            .id(id)
            .title(title)
            .description(description)
            .ownerName(ownerName)
            .ownerId(ownerId)
            .build();
    }
}
