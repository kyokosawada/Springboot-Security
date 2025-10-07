package com.exist.helpdesk.dto.helpdeskticket;

import jakarta.validation.constraints.*;

public record RemarkCreateRequestDTO(
        @NotBlank
        String remark,

        @NotBlank
        String addedBy
) {
}
