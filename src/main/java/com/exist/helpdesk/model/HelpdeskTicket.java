package com.exist.helpdesk.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HelpdeskTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String ticketNumber;

    private String title;

    private String body;

    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private Employee assignee;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    private String createdBy;

    private LocalDateTime updatedDate;

    private String updatedBy;

    @ElementCollection
    @CollectionTable(name = "ticket_remarks", joinColumns = @JoinColumn(name = "ticket_id"))
    private List<Remark> remarks = new ArrayList<>();

    @Version
    private Integer version;
}
