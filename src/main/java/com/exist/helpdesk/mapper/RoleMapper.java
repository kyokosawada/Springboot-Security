package com.exist.helpdesk.mapper;

import com.exist.helpdesk.dto.RoleResponseDTO;
import org.mapstruct.Mapper;
import com.exist.helpdesk.model.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponseDTO toResponse(Role role);
}
