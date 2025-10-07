package com.exist.helpdesk.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponseDTO(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        List<FieldError> errors
) {
    public static record FieldError(
            String field,
            String message
    ) {
    }
}