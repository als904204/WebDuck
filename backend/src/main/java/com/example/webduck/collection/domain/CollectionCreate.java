package com.example.webduck.collection.domain;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CollectionCreate {

    @NotBlank(message = "title must not be blank.")
    @Size(min = 2,max = 20,message = "title must be between 2 and 20 characters long")
    private String title;

    @NotBlank(message = "description must not be blank.")
    @Size(min = 2,max = 200,message = "description must be between 2 and 200 characters long")
    private String description;

    @NotEmpty(message = "webtoon ID list must not be empty")
    private List<Long> webtoonIds;

}
