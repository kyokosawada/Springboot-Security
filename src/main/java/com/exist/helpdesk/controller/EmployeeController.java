package com.exist.helpdesk.controller;
import com.exist.helpdesk.dto.EmployeeCreateRequestDTO;
import com.exist.helpdesk.dto.EmployeeUpdateRequestDTO;
import com.exist.helpdesk.model.Employee;
import com.exist.helpdesk.service.EmployeeService;
import com.exist.helpdesk.dto.EmployeeResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public EmployeeResponseDTO createEmployee(@Valid @RequestBody EmployeeCreateRequestDTO request) {
        return employeeService.createEmployee(request);
    }

    @PutMapping("/{id}")
    public EmployeeResponseDTO updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeUpdateRequestDTO request) {
        return employeeService.updateEmployee(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }

    @PatchMapping("/{id}/role/{roleId}")
    public ResponseEntity<EmployeeResponseDTO> assignRoleToEmployee(
            @PathVariable Long id, @PathVariable Long roleId) {
        EmployeeResponseDTO dto = employeeService.assignRoleToEmployee(id, roleId);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }
}
