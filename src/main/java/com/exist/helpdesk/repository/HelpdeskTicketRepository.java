package com.exist.helpdesk.repository;

import com.exist.helpdesk.model.HelpdeskTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HelpdeskTicketRepository extends JpaRepository<HelpdeskTicket, Long>, JpaSpecificationExecutor<HelpdeskTicket> {
}
