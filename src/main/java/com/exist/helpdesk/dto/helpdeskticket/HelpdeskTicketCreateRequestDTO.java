package com.exist.helpdesk.dto.helpdeskticket;

import jakarta.validation.constraints.*;

public record HelpdeskTicketCreateRequestDTO(
        @NotBlank
        String title,

        @NotBlank
        String name,

        @NotBlank
        String body,

        @NotNull
        Long assigneeId,

        Boolean filed
) {
}