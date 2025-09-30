package com.exist.helpdesk.service;

import com.exist.helpdesk.dto.RoleResponseDTO;
import com.exist.helpdesk.model.Role;
import com.exist.helpdesk.repository.RoleRepository;
import com.exist.helpdesk.dto.RoleCreateRequestDTO;
import com.exist.helpdesk.dto.RoleUpdateRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleResponseDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(role -> new RoleResponseDTO(role.getId(), role.getName()))
                .toList();
    }

    public RoleResponseDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id).orElse(null);
        if (role == null) return null;
        return new RoleResponseDTO(role.getId(), role.getName());
    }

    public RoleResponseDTO createRole(RoleCreateRequestDTO request) {
        Role role = new Role();
        role.setName(request.getName());
        role.setDescription(request.getDescription());
        Role saved = roleRepository.save(role);
        return new RoleResponseDTO(saved.getId(), saved.getName());
    }

    public RoleResponseDTO updateRole(Long id, RoleUpdateRequestDTO request) {
        Role role = roleRepository.findById(id).orElse(null);
        if (role == null) return null;
        if (request.getName() != null) role.setName(request.getName());
        if (request.getDescription() != null) role.setDescription(request.getDescription());
        Role saved = roleRepository.save(role);
        return new RoleResponseDTO(saved.getId(), saved.getName());
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    public Role getRoleEntityById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }
}
