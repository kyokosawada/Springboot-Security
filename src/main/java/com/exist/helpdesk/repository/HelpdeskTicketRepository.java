package com.exist.helpdesk.repository;

import com.exist.helpdesk.model.HelpdeskTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface HelpdeskTicketRepository extends JpaRepository<HelpdeskTicket, Long>, JpaSpecificationExecutor<HelpdeskTicket> {
    @Query("SELECT t FROM HelpdeskTicket t")
    List<HelpdeskTicket> findAllTickets();

    @Query("SELECT t FROM HelpdeskTicket t WHERE t.status = :status")
    List<HelpdeskTicket> findByStatus(@Param("status") String status);

    @Query("SELECT t FROM HelpdeskTicket t WHERE t.assignee.name = :name")
    List<HelpdeskTicket> findByAssigneeName(@Param("name") String name);

    @Query("SELECT t.id FROM HelpdeskTicket t WHERE t.createdBy = :creator")
    List<Long> findTicketIdsByCreator(@Param("creator") String creator);

    @Query("SELECT t FROM HelpdeskTicket t WHERE t.createdDate > :date")
    List<HelpdeskTicket> findByCreatedDateAfter(@Param("date") java.time.LocalDateTime date);

    @Query("SELECT t FROM HelpdeskTicket t WHERE t.status = :status AND t.assignee.name = :name")
    List<HelpdeskTicket> findByStatusAndAssigneeName(@Param("status") String status, @Param("name") String name);
}
