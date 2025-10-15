package com.exist.helpdesk.mapper;

import com.exist.helpdesk.model.Employee;
import com.exist.helpdesk.dto.employee.EmployeeCreateRequestDTO;
import com.exist.helpdesk.dto.employee.EmployeeUpdateRequestDTO;
import com.exist.helpdesk.dto.employee.EmployeeResponseDTO;
import org.mapstruct.*;
import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "password", ignore = true)
    Employee toEntity(EmployeeCreateRequestDTO dto);

    EmployeeResponseDTO toResponse(Employee employee);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEmployeeFromDto(EmployeeUpdateRequestDTO dto, @MappingTarget Employee entity);
}
