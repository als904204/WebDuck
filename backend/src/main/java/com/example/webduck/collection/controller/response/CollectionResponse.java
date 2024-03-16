package com.example.webduck.collection.controller.response;

import com.example.webduck.collection.domain.Collection;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CollectionResponse {

    private String title;
    private String description;
    private boolean isPublic;
    private String ownerName;

    public CollectionResponse(String title, String description, boolean isPublic,
        String ownerName) {
        this.title = title;
        this.description = description;
        this.isPublic = isPublic;
        this.ownerName = ownerName;
    }

    public static CollectionResponse from(Collection collection) {
        return CollectionResponse.builder()
            .title(collection.getTitle())
            .description(collection.getDescription())
            .isPublic(collection.isPublic())
            .ownerName(collection.getOwnerName())
            .build();
    }
}
