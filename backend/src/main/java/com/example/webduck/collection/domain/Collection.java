package com.example.webduck.collection.domain;

import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.member.domain.Member;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
@Builder
@Getter
public class Collection {

    private static final Logger logger = LoggerFactory.getLogger(Collection.class);

    private Long id;
    private String title;
    private String description;
    private Long ownerId;
    private String ownerName;
    private boolean isOwner;
    public static Collection from(Member member,CollectionCreate collectionCreate) {
        return Collection.builder()
            .title(collectionCreate.getTitle())
            .description(collectionCreate.getDescription())
            .ownerName(member.getUsername())
            .ownerId(member.getId())
            .isOwner(true)
            .build();
    }

    public static void flagAuthor(Member member, List<Collection> collections) {
        Long memberId = member.getId();
        for (Collection collection : collections) {
            if (collection.getOwnerId().equals(memberId)) {
                collection.setOwner();
            }
        }
    }

    public void validateOwner(Member member, Collection collection) {
        if (!collection.getOwnerId().equals(member.getId())) {
            logger.error("member id and requested member id are different");
            throw new CustomException(LogicExceptionCode.BAD_REQUEST);
        }
    }



    public Collection update(CollectionUpdate collectionUpdate) {
        return Collection.builder()
            .id(id)
            .isOwner(isOwner)
            .ownerId(ownerId)
            .ownerName(ownerName)
            .title(collectionUpdate.getTitle())
            .description(collectionUpdate.getDescription())
            .build();
    }

    private void setOwner() {
        this.isOwner = true;
    }
}
