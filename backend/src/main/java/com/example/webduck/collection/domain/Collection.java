package com.example.webduck.collection.domain;

import com.example.webduck.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Collection {
    private Long id;
    private String title;
    private String description;
    private boolean isPublic;
    private Long ownerId;
    private String ownerName;

    @Builder
    public Collection(Long id, String title, String description, boolean isPublic, Long ownerId,
        String ownerName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isPublic = isPublic;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
    }

    public static Collection from(Member member,CollectionCreate collectionCreate) {
        return Collection.builder()
            .title(collectionCreate.getTitle())
            .description(collectionCreate.getDescription())
            .isPublic(collectionCreate.isPublic())
            .ownerId(member.getId())
            .ownerName(member.getUsername())
            .build();
    }

}