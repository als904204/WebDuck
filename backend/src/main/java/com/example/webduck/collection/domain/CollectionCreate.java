package com.example.webduck.collection.domain;

import java.util.List;
import lombok.Getter;

@Getter
public class CollectionCreate {

    private String title;
    private String description;
    private boolean isPublic;
    private List<Long> webtoonIds;

}
