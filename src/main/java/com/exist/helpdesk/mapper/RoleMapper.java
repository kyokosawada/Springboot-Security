package com.exist.helpdesk.mapper;

import com.exist.helpdesk.dto.RoleCreateRequestDTO;
import com.exist.helpdesk.dto.RoleUpdateRequestDTO;
import com.exist.helpdesk.dto.RoleResponseDTO;
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
