package com.exist.helpdesk.controller;
import com.exist.helpdesk.dto.employee.EmployeeCreateRequestDTO;
import com.exist.helpdesk.dto.employee.EmployeeUpdateRequestDTO;
import com.exist.helpdesk.service.EmployeeService;
import com.exist.helpdesk.dto.employee.EmployeeResponseDTO;
import com.exist.helpdesk.dto.PaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public PaginatedResponse<EmployeeResponseDTO> getEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String employmentStatus,
            @RequestParam(required = false) Long roleId
    ) {
        return employeeService.getEmployees(page, size, sortBy, sortDir, name, age, address, phone, employmentStatus, roleId);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public EmployeeResponseDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public EmployeeResponseDTO createEmployee(@Valid @RequestBody EmployeeCreateRequestDTO request) {
        return employeeService.createEmployee(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public EmployeeResponseDTO updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeUpdateRequestDTO request) {
        return employeeService.updateEmployee(id, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/role/{roleId}")
    public ResponseEntity<EmployeeResponseDTO> assignRoleToEmployee(
            @PathVariable Long id, @PathVariable Long roleId) {
        EmployeeResponseDTO dto = employeeService.assignRoleToEmployee(id, roleId);
        return ResponseEntity.ok(dto);
    }
}
