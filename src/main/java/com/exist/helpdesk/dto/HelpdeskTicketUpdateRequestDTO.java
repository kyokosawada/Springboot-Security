package com.exist.helpdesk.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HelpdeskTicketUpdateRequestDTO {
    private String title;
    private String body;
    private String status;
    private Long assigneeId;
    private String updatedBy;
}
