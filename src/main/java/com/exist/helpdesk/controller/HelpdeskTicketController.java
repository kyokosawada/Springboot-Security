package com.exist.helpdesk.controller;

import com.exist.helpdesk.dto.*;
import com.exist.helpdesk.dto.helpdeskticket.*;
import com.exist.helpdesk.service.HelpdeskTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/tickets")
public class HelpdeskTicketController {
    private final HelpdeskTicketService ticketService;

    @Autowired
    public HelpdeskTicketController(HelpdeskTicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public HelpdeskTicketResponseDTO createTicket(@Valid @RequestBody HelpdeskTicketCreateRequestDTO dto) {
        return ticketService.createTicket(dto);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public HelpdeskTicketResponseDTO updateTicket(@PathVariable Long id, @Valid @RequestBody HelpdeskTicketUpdateRequestDTO dto) {
        return ticketService.updateTicket(id, dto);
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{id}/remarks")
    public RemarkResponseDTO addRemark(@PathVariable Long id, @Valid @RequestBody RemarkCreateRequestDTO dto) {
        return ticketService.addRemarkToTicket(id, dto);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public PaginatedResponse<HelpdeskTicketResponseDTO> getTickets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(defaultValue = "ticketNumber") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long assigneeId,
            @RequestParam(required = false) Long createdById) {
        return ticketService.getTickets(page, size, sortBy, sortDir, status, assigneeId, createdById);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public HelpdeskTicketResponseDTO getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}
