package com.example.webduck.global.common;

import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
public class SliceResponse<T> {

    private List<T> item;
    private int pageNumber;
    private int pageSize;
    private boolean hasNext;
    private int count;
    private Long nextId;
    public SliceResponse(List<T> item, Pageable pageable, boolean hasNext,Long nextId) {
        this.item = item;
        this.pageNumber = pageable.getPageNumber();
        this.pageSize = pageable.getPageSize();
        this.hasNext = hasNext;
        this.count = item.size();
        this.nextId = nextId;
    }
}
