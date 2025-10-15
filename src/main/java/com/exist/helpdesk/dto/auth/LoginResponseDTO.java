package com.exist.helpdesk.dto.auth;

public record LoginResponseDTO(
        String token,
        String username,
        String role) {
}

