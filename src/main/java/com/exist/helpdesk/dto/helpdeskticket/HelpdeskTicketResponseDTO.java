package com.exist.helpdesk.dto.helpdeskticket;

import java.time.LocalDateTime;
import java.util.List;

public record HelpdeskTicketResponseDTO(
        String ticketNumber,
        String title,
        String body,
        String status,
        Long assigneeId,
        LocalDateTime createdDate,
        String createdBy,
        LocalDateTime updatedDate,
        String updatedBy,
        List<RemarkResponseDTO> remarks
) {
}