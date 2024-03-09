package com.example.webduck.collection.controller.response;

import com.example.webduck.collection.domain.Collection;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CollectionResponse {

    private Long id;
    private String title;
    private String description;
    private boolean isPublic;
    private Long ownerId;
    private String ownerName;

    public CollectionResponse(Long id, String title, String description, boolean isPublic,
        Long ownerId,
        String ownerName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isPublic = isPublic;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
    }

    public static CollectionResponse from(Collection collection) {
        return CollectionResponse.builder()
            .id(collection.getId())
            .title(collection.getTitle())
            .description(collection.getDescription())
            .isPublic(collection.isPublic())
            .ownerId(collection.getOwnerId())
            .ownerName(collection.getOwnerName())
            .build();
    }
}
