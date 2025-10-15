package com.exist.helpdesk.repository;

import com.exist.helpdesk.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    @Query("SELECT e FROM Employee e")
    List<Employee> findAllEmployees();

    @Query("SELECT e FROM Employee e WHERE e.name = :name")
    List<Employee> findByName(@Param("name") String name);

    @Query("SELECT e FROM Employee e WHERE e.role.name = :roleName")
    List<Employee> findByRoleName(@Param("roleName") String roleName);

    @Query("SELECT e.id FROM Employee e WHERE e.age = :age")
    List<Long> findEmployeeIdsByAge(@Param("age") Integer age);

    @Query("SELECT e FROM Employee e WHERE e.employmentStatus = :status")
    List<Employee> findByEmploymentStatus(@Param("status") String status);

    @Query("SELECT e FROM Employee e WHERE e.name = :name AND e.age = :age")
    List<Employee> findByNameAndAge(@Param("name") String name, @Param("age") Integer age);

    @Query("SELECT e FROM Employee e JOIN FETCH e.role WHERE e.username = :username")
    Optional<Employee> findByUsername(@Param("username") String username);
}
