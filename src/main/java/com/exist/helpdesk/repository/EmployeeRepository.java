package com.exist.helpdesk.repository;

import com.exist.helpdesk.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // You can add custom query methods here (e.g. findByName)
}
