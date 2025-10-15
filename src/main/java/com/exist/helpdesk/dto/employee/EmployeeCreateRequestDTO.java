package com.exist.helpdesk.dto.employee;

import jakarta.validation.constraints.*;

public record EmployeeCreateRequestDTO(
        @NotBlank
        String name,

        @NotNull @Min(18)
        Integer age,

        @NotBlank
        String address,

        @NotBlank
        @Pattern(regexp = "^\\+?[0-9]{7,15}$")
        String phone,

        @NotBlank
        String employmentStatus,

        @NotBlank
        String username,

        @NotNull
        Long roleId,

        @NotBlank
        String password
) {
}