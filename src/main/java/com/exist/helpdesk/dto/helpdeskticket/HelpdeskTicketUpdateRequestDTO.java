package com.exist.helpdesk.dto.helpdeskticket;

public record HelpdeskTicketUpdateRequestDTO(
        String title,
        String body,
        String status,
        Long assigneeId,
        String updatedBy
) {
}