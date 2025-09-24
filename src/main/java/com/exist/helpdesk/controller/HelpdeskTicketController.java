package com.exist.helpdesk.controller;

import com.exist.helpdesk.model.HelpdeskTicket;
import com.exist.helpdesk.service.HelpdeskTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class HelpdeskTicketController {
    private final HelpdeskTicketService ticketService;

    @Autowired
    public HelpdeskTicketController(HelpdeskTicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public List<HelpdeskTicket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    public HelpdeskTicket getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id);
    }

    @PostMapping
    public HelpdeskTicket createTicket(@RequestBody HelpdeskTicket ticket) {
        return ticketService.createTicket(ticket);
    }

    @PutMapping("/{id}")
    public HelpdeskTicket updateTicket(@PathVariable Long id, @RequestBody HelpdeskTicket ticket) {
        return ticketService.updateTicket(id, ticket);
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
    }
}
