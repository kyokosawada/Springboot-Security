package com.exist.helpdesk.service;

import com.exist.helpdesk.dto.EmployeeResponseDTO;
import com.exist.helpdesk.model.Employee;
import com.exist.helpdesk.repository.EmployeeRepository;
import com.exist.helpdesk.model.Role;
import com.exist.helpdesk.dto.EmployeeCreateRequestDTO;
import com.exist.helpdesk.dto.EmployeeUpdateRequestDTO;
import com.exist.helpdesk.mapper.EmployeeMapper;
import com.exist.helpdesk.dto.PaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.ArrayList;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final RoleService roleService;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, RoleService roleService, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.roleService = roleService;
        this.employeeMapper = employeeMapper;
    }


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
        List<EmployeeResponseDTO> content = employeePage.getContent().stream()
                .map(employeeMapper::toResponse)
                .toList();
        return PaginatedResponse.<EmployeeResponseDTO>builder()
                .content(content)
                .page(employeePage.getNumber())
                .size(employeePage.getSize())
                .totalElements(employeePage.getTotalElements())
                .totalPages(employeePage.getTotalPages())
                .last(employeePage.isLast())
                .build();
    }

    public EmployeeResponseDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        return employee == null ? null : employeeMapper.toResponse(employee);
    }

    public Employee getEmployeeEntityById(Long id) {
        return employeeRepository.findById(id).orElseThrow();
    }

    public EmployeeResponseDTO createEmployee(EmployeeCreateRequestDTO request) {
        Role role = roleService.getRoleEntityById(request.getRoleId());
        Employee employee = employeeMapper.toEntity(request);
        employee.setRole(role);

        Employee saved = employeeRepository.save(employee);
        return employeeMapper.toResponse(saved);
    }

    public EmployeeResponseDTO updateEmployee(Long id, EmployeeUpdateRequestDTO request) {
        Employee emp = employeeRepository.findById(id).orElse(null);
        if (emp == null) return null;
        employeeMapper.updateEmployeeFromDto(request, emp);
        if (request.getRoleId() != null) {
            emp.setRole(roleService.getRoleEntityById(request.getRoleId()));
        }
        Employee saved = employeeRepository.save(emp);
        return employeeMapper.toResponse(saved);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public EmployeeResponseDTO assignRoleToEmployee(Long empId, Long roleId) {
        Employee emp = employeeRepository.findById(empId).orElse(null);
        if (emp == null) return null;
        Role role = roleService.getRoleEntityById(roleId);
        if (role == null) return null;
        emp.setRole(role);
        Employee saved = employeeRepository.save(emp);
        return employeeMapper.toResponse(saved);
    }

}
