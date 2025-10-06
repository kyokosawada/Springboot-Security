package com.exist.helpdesk.mapper;

import com.exist.helpdesk.dto.role.RoleCreateRequestDTO;
import com.exist.helpdesk.dto.role.RoleUpdateRequestDTO;
import com.exist.helpdesk.dto.role.RoleResponseDTO;
import com.exist.helpdesk.model.Role;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toEntity(RoleCreateRequestDTO request);

    RoleResponseDTO toResponse(Role entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRoleFromDto(RoleUpdateRequestDTO dto, @MappingTarget Role entity);
}
