package com.exist.helpdesk.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HelpdeskTicketCreateRequestDTO {
    @NotBlank
    private String title;

    @NotBlank
    private String name;

    @NotBlank
    private String body;

    @NotNull
    private Long assigneeId;

    private Boolean filed;
}
