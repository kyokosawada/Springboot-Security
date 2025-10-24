package com.exist.helpdesk.controller;

import com.exist.helpdesk.service.RoleService;
import com.exist.helpdesk.dto.role.RoleCreateRequestDTO;
import com.exist.helpdesk.dto.role.RoleUpdateRequestDTO;
import com.exist.helpdesk.dto.role.RoleResponseDTO;
import com.exist.helpdesk.dto.PaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public PaginatedResponse<RoleResponseDTO> getRoles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String name
    ) {
        return roleService.getRoles(page, size, sortBy, sortDir, name);
    }

    @GetMapping("/{id}")
    public RoleResponseDTO getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public RoleResponseDTO createRole(@RequestBody RoleCreateRequestDTO request) {
        return roleService.createRole(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public RoleResponseDTO updateRole(@PathVariable Long id, @RequestBody RoleUpdateRequestDTO request) {
        return roleService.updateRole(id, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
    }
}
