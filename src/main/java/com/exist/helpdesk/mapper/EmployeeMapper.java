package com.exist.helpdesk.mapper;

import com.exist.helpdesk.model.Employee;
import com.exist.helpdesk.model.Role;
import com.exist.helpdesk.dto.EmployeeRequestDTO;
import com.exist.helpdesk.dto.EmployeeResponseDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface EmployeeMapper {
    Employee toEntity(EmployeeRequestDTO request);
    EmployeeResponseDTO toResponse(Employee employee);
}
