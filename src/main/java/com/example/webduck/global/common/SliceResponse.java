package com.example.webduck.global.common;

import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
public class SliceResponse<T> {

    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private boolean hasNext;
    private int count;
    private Long nextId;
    public SliceResponse(List<T> content, Pageable pageable, boolean hasNext,Long nextId) {
        this.content = content;
        this.pageNumber = pageable.getPageNumber();
        this.pageSize = pageable.getPageSize();
        this.hasNext = hasNext;
        this.count = content.size();
        this.nextId = nextId;
    }
}
