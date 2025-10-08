package com.exist.helpdesk.service;

import com.exist.helpdesk.dto.PaginatedResponse;
import com.exist.helpdesk.dto.role.RoleResponseDTO;
import com.exist.helpdesk.dto.role.RoleCreateRequestDTO;
import com.exist.helpdesk.dto.role.RoleUpdateRequestDTO;
import com.exist.helpdesk.model.Role;

import java.util.List;

public interface RoleService {
    List<RoleResponseDTO> getAllRoles();

    RoleResponseDTO getRoleById(Long id);

    PaginatedResponse<RoleResponseDTO> getRoles(int page, int size, String sortBy, String sortDir, String name);

    RoleResponseDTO createRole(RoleCreateRequestDTO request);

    RoleResponseDTO updateRole(Long id, RoleUpdateRequestDTO request);

    void deleteRole(Long id);

    Role getRoleEntityById(Long id);
}
