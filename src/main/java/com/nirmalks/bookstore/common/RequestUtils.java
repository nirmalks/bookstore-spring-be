package com.nirmalks.bookstore.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class RequestUtils {
    public static Pageable getPageable(int page, int size, String sortKey, String sortOrder) {
        return PageRequest.of(page, size, Sort.by(Sort.Order.by(sortKey).with(Sort.Direction.fromString(sortOrder))));
    }

    public static Pageable getPageable(PageRequestDto pageRequestDto) {
        return PageRequest.of(pageRequestDto.getPage(),
                pageRequestDto.getSize(),
                Sort.by(Sort.Order.by(pageRequestDto.getSortKey()).with(Sort.Direction.fromString(pageRequestDto.getSortOrder()))));
    }
}
