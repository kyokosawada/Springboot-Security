package com.exist.helpdesk.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeCreateRequestDTO {
    @NotBlank
    private String name;

    @NotNull
    @Min(18)
    private Integer age;

    @NotBlank
    private String address;

    @NotBlank
    @Pattern(regexp = "^\\+?[0-9]{7,15}$")
    private String phone;

    @NotBlank
    private String employmentStatus;

    @NotNull
    private Long roleId;
}
