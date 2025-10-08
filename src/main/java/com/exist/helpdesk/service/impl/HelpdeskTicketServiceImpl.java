package com.exist.helpdesk.service.impl;

import com.exist.helpdesk.dto.*;
import com.exist.helpdesk.dto.helpdeskticket.*;
import com.exist.helpdesk.model.HelpdeskTicket;
import com.exist.helpdesk.model.Remark;
import com.exist.helpdesk.mapper.HelpdeskTicketMapper;
import com.exist.helpdesk.repository.HelpdeskTicketRepository;
import com.exist.helpdesk.service.HelpdeskTicketService;
import com.exist.helpdesk.service.EmployeeService;
import com.exist.helpdesk.utils.PaginatedResponseUtil;
import com.exist.helpdesk.dto.PaginatedResponse;
import com.exist.helpdesk.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class HelpdeskTicketServiceImpl implements HelpdeskTicketService {
    private final HelpdeskTicketRepository ticketRepository;
    private final EmployeeService employeeService;
    private final HelpdeskTicketMapper ticketMapper;

    public HelpdeskTicketServiceImpl(HelpdeskTicketRepository ticketRepository, EmployeeService employeeService, HelpdeskTicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.employeeService = employeeService;
        this.ticketMapper = ticketMapper;
    }

    @Override
    @Transactional
    public HelpdeskTicketResponseDTO createTicket(HelpdeskTicketCreateRequestDTO dto) {
        HelpdeskTicket ticket = ticketMapper.toEntity(dto);
        ticket.setAssignee(employeeService.getEmployeeEntityById(dto.assigneeId()));
        ticket.setCreatedDate(LocalDateTime.now());
        ticket.setUpdatedDate(LocalDateTime.now());
        ticket.setStatus((dto.filed() != null && !dto.filed()) ? "draft" : "filed");
        ticket.setRemarks(new ArrayList<>());
        ticket.setTicketNumber("EXIST-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        HelpdeskTicket saved = ticketRepository.save(ticket);
        return ticketMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public HelpdeskTicketResponseDTO updateTicket(Long id, HelpdeskTicketUpdateRequestDTO dto) {
        HelpdeskTicket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket with id " + id + " not found"));
        ticketMapper.updateEntityFromDto(dto, ticket);
        if (dto.assigneeId() != null) {
            ticket.setAssignee(employeeService.getEmployeeEntityById(dto.assigneeId()));
        }
        ticket.setUpdatedDate(LocalDateTime.now());
        HelpdeskTicket saved = ticketRepository.save(ticket);
        return ticketMapper.toResponse(saved);
    }

    @Override
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

    @Override
    public HelpdeskTicketResponseDTO getTicketById(Long id) {
        HelpdeskTicket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket with id " + id + " not found"));
        return ticketMapper.toResponse(ticket);
    }

    @Override
    @Transactional
    public void deleteTicket(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ticket with id " + id + " not found");
        }
        ticketRepository.deleteById(id);
    }

    @Override
    @Transactional
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