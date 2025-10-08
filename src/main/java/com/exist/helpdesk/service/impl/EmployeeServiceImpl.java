// ... existing code ...

package com.exist.helpdesk.service.impl;

import com.exist.helpdesk.dto.employee.EmployeeResponseDTO;
import com.exist.helpdesk.model.Employee;
import com.exist.helpdesk.repository.EmployeeRepository;
import com.exist.helpdesk.model.Role;
import com.exist.helpdesk.dto.employee.EmployeeCreateRequestDTO;
import com.exist.helpdesk.dto.employee.EmployeeUpdateRequestDTO;
import com.exist.helpdesk.mapper.EmployeeMapper;
import com.exist.helpdesk.dto.PaginatedResponse;
import com.exist.helpdesk.service.EmployeeService;
import com.exist.helpdesk.service.RoleService;
import com.exist.helpdesk.utils.PaginatedResponseUtil;
import com.exist.helpdesk.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;
import java.util.ArrayList;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final RoleService roleService;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, RoleService roleService, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.roleService = roleService;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public PaginatedResponse<EmployeeResponseDTO> getEmployees(
            int page, int size, String sortBy, String sortDir, String name, Integer age,
            String address, String phone, String employmentStatus, Long roleId) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        List<Specification<Employee>> specs = new ArrayList<>();
        if (name != null && !name.isBlank()) {
            specs.add((root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if (age != null) {
            specs.add((root, query, cb) -> cb.equal(root.get("age"), age));
        }
        if (address != null && !address.isBlank()) {
            specs.add((root, query, cb) -> cb.like(cb.lower(root.get("address")), "%" + address.toLowerCase() + "%"));
        }
        if (phone != null && !phone.isBlank()) {
            specs.add((root, query, cb) -> cb.like(cb.lower(root.get("phone")), "%" + phone.toLowerCase() + "%"));
        }
        if (employmentStatus != null && !employmentStatus.isBlank()) {
            specs.add((root, query, cb) -> cb.equal(root.get("employmentStatus"), employmentStatus.toLowerCase()));
        }
        if (roleId != null) {
            specs.add((root, query, cb) -> cb.equal(root.get("role").get("id"), roleId));
        }
        Specification<Employee> spec = specs.stream()
                .reduce(Specification::and)
                .orElse((root, query, cb) -> cb.conjunction());
        Page<Employee> employeePage = employeeRepository.findAll(spec, pageable);
        Page<EmployeeResponseDTO> dtoPage = employeePage.map(employeeMapper::toResponse);
        return PaginatedResponseUtil.fromPage(dtoPage);
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " not found"));
        return employeeMapper.toResponse(employee);
    }

    @Override
    public Employee getEmployeeEntityById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " not found"));
    }

    @Override
    @Transactional
    public EmployeeResponseDTO createEmployee(EmployeeCreateRequestDTO dto) {
        Employee employee = employeeMapper.toEntity(dto);
        Role role = roleService.getRoleEntityById(dto.roleId());
        employee.setRole(role);
        Employee saved = employeeRepository.save(employee);
        return employeeMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeUpdateRequestDTO request) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " not found"));
        employeeMapper.updateEmployeeFromDto(request, emp);
        if (request.roleId() != null) {
            Role role = roleService.getRoleEntityById(request.roleId());
            emp.setRole(role);
        }
        Employee saved = employeeRepository.save(emp);
        return employeeMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public EmployeeResponseDTO assignRoleToEmployee(Long empId, Long roleId) {
        Employee emp = employeeRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id " + empId + " not found"));
        Role role = roleService.getRoleEntityById(roleId);
        emp.setRole(role);
        Employee saved = employeeRepository.save(emp);
        return employeeMapper.toResponse(saved);
    }
}