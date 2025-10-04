package com.exist.helpdesk.utils;

import org.springframework.data.domain.Page;
import com.exist.helpdesk.dto.PaginatedResponse;

public class PaginatedResponseUtil {
    private PaginatedResponseUtil() {
    }

    public static <T> PaginatedResponse<T> fromPage(Page<T> page) {
        return PaginatedResponse.<T>builder()
                .content(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}
