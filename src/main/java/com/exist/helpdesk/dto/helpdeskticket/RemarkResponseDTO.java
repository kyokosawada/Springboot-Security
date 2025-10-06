package com.exist.helpdesk.dto.helpdeskticket;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemarkResponseDTO {
    private String remark;
    private String addedBy;
    private LocalDateTime addedAt;
}
