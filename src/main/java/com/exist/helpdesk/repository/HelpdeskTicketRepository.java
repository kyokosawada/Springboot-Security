package com.exist.helpdesk.repository;

import com.exist.helpdesk.model.HelpdeskTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HelpdeskTicketRepository extends JpaRepository<HelpdeskTicket, Long> {
}
