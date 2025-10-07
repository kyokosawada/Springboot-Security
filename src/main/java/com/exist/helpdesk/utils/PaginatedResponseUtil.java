package com.exist.helpdesk.utils;

import org.springframework.data.domain.Page;
import com.exist.helpdesk.dto.PaginatedResponse;

public class PaginatedResponseUtil {
    private PaginatedResponseUtil() {
    }

    public static <T> PaginatedResponse<T> fromPage(Page<T> page) {
        return new PaginatedResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }
}
