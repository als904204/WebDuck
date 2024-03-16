package com.example.webduck.collection.domain;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CollectionCreate {

    @NotBlank(message = "컬렉션 제목은 빈칸이 될 수 없습니다")
    @Size(max = 20,message = "제목은 최대 20자까지 작성할 수 있습니다")
    private String title;

    @NotBlank(message = "컬렉션 설명은 빈칸이 될 수 없습니다")
    @Size(max = 200,message = "설명은 최대 200자까지 작성할 수 있습니다")
    private String description;

    @NotNull(message = "isPublic 은 빈값이 될 수 없습니다")
    private boolean isPublic;

    @NotNull(message = "webtoonIds 은 빈값이 될 수 없습니다")
    private List<Long> webtoonIds;

}
