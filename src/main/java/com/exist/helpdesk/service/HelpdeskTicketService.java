package com.exist.helpdesk.service;

import com.exist.helpdesk.model.HelpdeskTicket;
import com.exist.helpdesk.repository.HelpdeskTicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class HelpdeskTicketService {

    private final HelpdeskTicketRepository ticketRepository;

    @Autowired
    public HelpdeskTicketService(HelpdeskTicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<HelpdeskTicket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public HelpdeskTicket getTicketById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public HelpdeskTicket createTicket(HelpdeskTicket ticket) {
        return ticketRepository.save(ticket);
    }

    public HelpdeskTicket updateTicket(Long id, HelpdeskTicket updatedTicket) {
        if (ticketRepository.existsById(id)) {
            updatedTicket.setTicketNumber(id);
            return ticketRepository.save(updatedTicket);
        }
        return null;
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }
}
