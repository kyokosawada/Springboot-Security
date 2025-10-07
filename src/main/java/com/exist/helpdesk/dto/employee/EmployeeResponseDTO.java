package com.exist.helpdesk.dto.employee;

import com.exist.helpdesk.dto.role.RoleResponseDTO;

public record EmployeeResponseDTO(
        Long id,
        String name,
        Integer age,
        String address,
        String phone,
        String employmentStatus,
        RoleResponseDTO role
) {
}