package com.exist.helpdesk.service;

import com.exist.helpdesk.model.Role;
import com.exist.helpdesk.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class RoleService {
    // This service encapsulates CRUD operations for job roles/positions.
    // Could later prevent deletion if assigned, enforce uniqueness, etc.
    // Delegates to RoleRepository (to be created next).

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public Role updateRole(Long id, Role updatedRole) {
        if (roleRepository.existsById(id)) {
            updatedRole.setId(id);
            return roleRepository.save(updatedRole);
        }
        return null;
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
