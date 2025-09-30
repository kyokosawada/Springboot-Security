package com.exist.helpdesk.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeUpdateRequestDTO {
    private String name;
    private Integer age;
    private String address;
    private String phone;
    private String employmentStatus;
    private Long roleId;
}
