package com.exist.helpdesk.dto.helpdeskticket;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HelpdeskTicketResponseDTO {
    private String ticketNumber;
    private String title;
    private String body;
    private String status;
    private Long assigneeId;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime updatedDate;
    private String updatedBy;
    private List<RemarkResponseDTO> remarks;
}
