package com.exist.helpdesk.model;

import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

import lombok.*;
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Remark {
    private String remark;
    private String addedBy;
    private LocalDateTime addedAt;
}
