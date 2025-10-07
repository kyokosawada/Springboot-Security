package com.exist.helpdesk.dto.helpdeskticket;

import java.time.LocalDateTime;

public record RemarkResponseDTO(
        String remark,
        String addedBy,
        LocalDateTime addedAt
) {
}