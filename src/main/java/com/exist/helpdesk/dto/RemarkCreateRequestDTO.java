package com.exist.helpdesk.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemarkCreateRequestDTO {
    @NotBlank
    private String remark;
    @NotBlank
    private String addedBy;
}
