package com.exist.helpdesk.dto.role;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleResponseDTO {
    private Long id;
    private String name;
}
