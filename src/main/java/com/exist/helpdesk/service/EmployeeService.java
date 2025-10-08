package com.exist.helpdesk.service;

import com.exist.helpdesk.dto.employee.EmployeeResponseDTO;
import com.exist.helpdesk.model.Employee;
import com.exist.helpdesk.dto.employee.EmployeeCreateRequestDTO;
import com.exist.helpdesk.dto.employee.EmployeeUpdateRequestDTO;
import com.exist.helpdesk.dto.PaginatedResponse;

public interface EmployeeService {

    PaginatedResponse<EmployeeResponseDTO> getEmployees(
            int page, int size, String sortBy, String sortDir, String name, Integer age,
            String address, String phone, String employmentStatus, Long roleId);

    EmployeeResponseDTO getEmployeeById(Long id);

    Employee getEmployeeEntityById(Long id);

    EmployeeResponseDTO createEmployee(EmployeeCreateRequestDTO dto);

    EmployeeResponseDTO updateEmployee(Long id, EmployeeUpdateRequestDTO request);

    void deleteEmployee(Long id);

    EmployeeResponseDTO assignRoleToEmployee(Long empId, Long roleId);

}
