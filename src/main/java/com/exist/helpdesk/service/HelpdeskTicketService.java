package com.exist.helpdesk.service;

import com.exist.helpdesk.dto.*;
import com.exist.helpdesk.dto.helpdeskticket.*;
import com.exist.helpdesk.mapper.HelpdeskTicketMapper;
import com.exist.helpdesk.model.HelpdeskTicket;
import com.exist.helpdesk.model.Remark;
import com.exist.helpdesk.repository.HelpdeskTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import com.exist.helpdesk.exception.ResourceNotFoundException;

import com.exist.helpdesk.utils.PaginatedResponseUtil;

@Service
public class HelpdeskTicketService {
    private final HelpdeskTicketRepository ticketRepository;
    private final EmployeeService employeeService;
    private final HelpdeskTicketMapper ticketMapper;

    @Autowired
    public HelpdeskTicketService(HelpdeskTicketRepository ticketRepository, EmployeeService employeeService, HelpdeskTicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.employeeService = employeeService;
        this.ticketMapper = ticketMapper;
    }


    public HelpdeskTicketResponseDTO createTicket(HelpdeskTicketCreateRequestDTO dto) {
        HelpdeskTicket ticket = ticketMapper.toEntity(dto);
        ticket.setAssignee(employeeService.getEmployeeEntityById(dto.getAssigneeId()));
        ticket.setCreatedDate(LocalDateTime.now());
        ticket.setUpdatedDate(LocalDateTime.now());
        ticket.setStatus((dto.getFiled() != null && !dto.getFiled()) ? "draft" : "filed");
        ticket.setRemarks(new ArrayList<>());
        ticket.setTicketNumber("EXIST-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        HelpdeskTicket saved = ticketRepository.save(ticket);
        return ticketMapper.toResponse(saved);
    }

    public HelpdeskTicketResponseDTO updateTicket(Long id, HelpdeskTicketUpdateRequestDTO dto) {
        HelpdeskTicket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket with id " + id + " not found"));
        ticketMapper.updateEntityFromDto(dto, ticket);
        if (dto.getAssigneeId() != null) {
            ticket.setAssignee(employeeService.getEmployeeEntityById(dto.getAssigneeId()));
        }
        ticket.setUpdatedDate(LocalDateTime.now());
        HelpdeskTicket saved = ticketRepository.save(ticket);
        return ticketMapper.toResponse(saved);
    }

    public PaginatedResponse<HelpdeskTicketResponseDTO> getTickets(int page, int size, String sortBy, String sortDir, String statusFilter, Long assigneeId, Long createdById) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        List<Specification<HelpdeskTicket>> specs = new ArrayList<>();
        if (statusFilter != null && !statusFilter.isBlank())
            specs.add((root, query, cb) -> cb.equal(root.get("status"), statusFilter));
        if (assigneeId != null)
            specs.add((root, query, cb) -> cb.equal(root.get("assignee").get("id"), assigneeId));
        if (createdById != null)
            specs.add((root, query, cb) -> cb.equal(root.get("createdBy"), createdById.toString()));
        Specification<HelpdeskTicket> spec = specs.stream()
                .reduce(Specification::and)
                .orElse((root, query, cb) -> cb.conjunction());

        Page<HelpdeskTicket> pageResult = ticketRepository.findAll(spec, pageable);
        Page<HelpdeskTicketResponseDTO> dtoPage = pageResult.map(ticketMapper::toResponse);
        return PaginatedResponseUtil.fromPage(dtoPage);
    }

    public HelpdeskTicketResponseDTO getTicketById(Long id) {
        HelpdeskTicket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket with id " + id + " not found"));
        return ticketMapper.toResponse(ticket);
    }

    public void deleteTicket(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ticket with id " + id + " not found");
        }
        ticketRepository.deleteById(id);
    }

    public RemarkResponseDTO addRemarkToTicket(Long ticketId, RemarkCreateRequestDTO dto) {
        HelpdeskTicket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket with id " + ticketId + " not found"));
        Remark remark = ticketMapper.toEntity(dto);
        remark.setAddedAt(LocalDateTime.now());
        ticket.getRemarks().add(remark);
        ticket.setUpdatedDate(LocalDateTime.now());
        ticketRepository.save(ticket);
        return ticketMapper.remarkToDto(remark);
    }


}