package com.exist.helpdesk.mapper;

import com.exist.helpdesk.model.Employee;
import com.exist.helpdesk.model.Role;
import com.exist.helpdesk.dto.EmployeeCreateRequestDTO;
import com.exist.helpdesk.dto.EmployeeUpdateRequestDTO;
import com.exist.helpdesk.dto.EmployeeResponseDTO;
import org.mapstruct.*;
import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface EmployeeMapper {
    Employee toEntity(EmployeeCreateRequestDTO request);

    EmployeeResponseDTO toResponse(Employee employee);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEmployeeFromDto(EmployeeUpdateRequestDTO dto, @MappingTarget Employee entity);
}
