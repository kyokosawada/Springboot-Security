package com.exist.helpdesk.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank
        String username,

        @NotBlank
        String password
) {
}

