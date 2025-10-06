package com.exist.helpdesk.dto.employee;

import com.exist.helpdesk.dto.role.RoleResponseDTO;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponseDTO {
    private Long id;
    private String name;
    private Integer age;
    private String address;
    private String phone;
    private String employmentStatus;
    private RoleResponseDTO role;
}
