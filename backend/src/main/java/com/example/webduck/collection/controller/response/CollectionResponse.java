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
    private String ownerName;
    private boolean isOwner;

    public CollectionResponse(Long id, String title, String description, String ownerName,
        boolean isOwner) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.ownerName = ownerName;
        this.isOwner = isOwner;
    }

    public static CollectionResponse from(Collection collection) {
        return CollectionResponse.builder()
            .id(collection.getId())
            .title(collection.getTitle())
            .isOwner(collection.isOwner())
            .description(collection.getDescription())
            .ownerName(collection.getOwnerName())
            .build();
    }

}
