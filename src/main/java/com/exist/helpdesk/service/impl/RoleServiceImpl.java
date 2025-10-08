// ... existing code ...

package com.exist.helpdesk.service.impl;

import com.exist.helpdesk.dto.PaginatedResponse;
import com.exist.helpdesk.dto.role.RoleResponseDTO;
import com.exist.helpdesk.dto.role.RoleCreateRequestDTO;
import com.exist.helpdesk.dto.role.RoleUpdateRequestDTO;
import com.exist.helpdesk.model.Role;
import com.exist.helpdesk.repository.RoleRepository;
import com.exist.helpdesk.mapper.RoleMapper;
import com.exist.helpdesk.service.RoleService;
import com.exist.helpdesk.utils.PaginatedResponseUtil;
import com.exist.helpdesk.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleResponseDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(roleMapper::toResponse)
                .toList();
    }

    @Override
    public RoleResponseDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role with id " + id + " not found"));
        return roleMapper.toResponse(role);
    }

    @Override
    public PaginatedResponse<RoleResponseDTO> getRoles(int page, int size, String sortBy, String sortDir, String name) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Role> spec = (root, query, cb) -> cb.conjunction();
        if (name != null && !name.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }
        Page<Role> rolePage = roleRepository.findAll(spec, pageable);
        Page<RoleResponseDTO> dtoPage = rolePage.map(roleMapper::toResponse);
        return PaginatedResponseUtil.fromPage(dtoPage);
    }

    @Override
    @Transactional
    public RoleResponseDTO createRole(RoleCreateRequestDTO request) {
        Role role = roleMapper.toEntity(request);
        Role saved = roleRepository.save(role);
        return roleMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public RoleResponseDTO updateRole(Long id, RoleUpdateRequestDTO request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role with id " + id + " not found"));
        roleMapper.updateRoleFromDto(request, role);
        Role saved = roleRepository.save(role);
        return roleMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role getRoleEntityById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role with id " + id + " not found"));
    }
}