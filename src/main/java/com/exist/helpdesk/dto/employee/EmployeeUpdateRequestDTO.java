package com.exist.helpdesk.dto.employee;

public record EmployeeUpdateRequestDTO(
        String name,
        Integer age,
        String address,
        String phone,
        String employmentStatus,
        Long roleId
) {
}