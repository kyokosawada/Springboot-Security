package com.exist.helpdesk.service;

import com.exist.helpdesk.dto.RoleResponseDTO;
import com.exist.helpdesk.model.Role;
import com.exist.helpdesk.repository.RoleRepository;
import com.exist.helpdesk.dto.RoleCreateRequestDTO;
import com.exist.helpdesk.dto.RoleUpdateRequestDTO;
import com.exist.helpdesk.mapper.RoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.exist.helpdesk.dto.PaginatedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public List<RoleResponseDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(roleMapper::toResponse)
                .toList();
    }

    public RoleResponseDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id).orElse(null);
        if (role == null) return null;
        return roleMapper.toResponse(role);
    }

    public PaginatedResponse<RoleResponseDTO> getRoles(int page, int size, String sortBy, String sortDir, String name) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Role> spec = (root, query, cb) -> cb.conjunction();
        if (name != null && !name.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }
        Page<Role> rolePage = roleRepository.findAll(spec, pageable);
        List<RoleResponseDTO> content = rolePage.getContent().stream()
                .map(roleMapper::toResponse)
                .toList();
        return PaginatedResponse.<RoleResponseDTO>builder()
                .content(content)
                .page(rolePage.getNumber())
                .size(rolePage.getSize())
                .totalElements(rolePage.getTotalElements())
                .totalPages(rolePage.getTotalPages())
                .last(rolePage.isLast())
                .build();
    }

    public RoleResponseDTO createRole(RoleCreateRequestDTO request) {
        Role role = roleMapper.toEntity(request);
        Role saved = roleRepository.save(role);
        return roleMapper.toResponse(saved);
    }

    public RoleResponseDTO updateRole(Long id, RoleUpdateRequestDTO request) {
        Role role = roleRepository.findById(id).orElse(null);
        if (role == null) return null;
        roleMapper.updateRoleFromDto(request, role);
        Role saved = roleRepository.save(role);
        return roleMapper.toResponse(saved);
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    public Role getRoleEntityById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }
}
