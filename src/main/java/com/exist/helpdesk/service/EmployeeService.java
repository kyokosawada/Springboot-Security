package com.exist.helpdesk.service;

import com.exist.helpdesk.dto.EmployeeResponseDTO;
import com.exist.helpdesk.model.Employee;
import com.exist.helpdesk.repository.EmployeeRepository;
import com.exist.helpdesk.model.Role;
import com.exist.helpdesk.dto.EmployeeCreateRequestDTO;
import com.exist.helpdesk.dto.EmployeeUpdateRequestDTO;
import com.exist.helpdesk.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
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
