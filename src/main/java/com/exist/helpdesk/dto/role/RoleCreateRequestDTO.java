package com.exist.helpdesk.dto.role;

import jakarta.validation.constraints.*;

public record RoleCreateRequestDTO(
        @NotBlank
        String name
) {
}