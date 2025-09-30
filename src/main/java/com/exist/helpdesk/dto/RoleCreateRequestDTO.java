package com.exist.helpdesk.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleCreateRequestDTO {
    @NotBlank
    private String name;
    private String description;
}
