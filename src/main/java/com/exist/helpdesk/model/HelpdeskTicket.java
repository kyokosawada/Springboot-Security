package com.exist.helpdesk.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "helpdesk_tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HelpdeskTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketNumber;

    @NotBlank
    private String title;
    @NotBlank
    private String body;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private Employee assignee;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private Employee createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by_id")
    private Employee updatedBy;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    private String remarks;

    public enum Status {
        DRAFT, FILED, INPROGRESS, CLOSED, DUPLICATE
    }

}
