package com.exist.helpdesk.service;

import com.exist.helpdesk.dto.*;
import com.exist.helpdesk.dto.helpdeskticket.*;
import com.exist.helpdesk.dto.PaginatedResponse;

public interface HelpdeskTicketService {
    HelpdeskTicketResponseDTO createTicket(HelpdeskTicketCreateRequestDTO dto);

    HelpdeskTicketResponseDTO updateTicket(Long id, HelpdeskTicketUpdateRequestDTO dto);

    PaginatedResponse<HelpdeskTicketResponseDTO> getTickets(
            int page, int size, String sortBy, String sortDir, String statusFilter,
            Long assigneeId, Long createdById);

    HelpdeskTicketResponseDTO getTicketById(Long id);

    void deleteTicket(Long id);

    RemarkResponseDTO addRemarkToTicket(Long ticketId, RemarkCreateRequestDTO dto);
}