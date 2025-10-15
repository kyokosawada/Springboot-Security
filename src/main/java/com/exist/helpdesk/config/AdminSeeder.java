package com.exist.helpdesk.config;

import com.exist.helpdesk.model.Employee;
import com.exist.helpdesk.model.Role;
import com.exist.helpdesk.repository.EmployeeRepository;
import com.exist.helpdesk.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import java.util.Optional;

@Component
public class AdminSeeder implements CommandLineRunner {
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminSeeder(EmployeeRepository employeeRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        String adminUsername = "admin";
        String adminPassword = "password";
        if (employeeRepository.findByUsername(adminUsername).isEmpty()) {
            Role adminRole = roleRepository.findByName("ADMIN").stream().findFirst()
                    .orElseGet(() -> roleRepository.save(Role.builder().name("ADMIN").build()));
            Employee admin = Employee.builder()
                    .username(adminUsername)
                    .password(passwordEncoder.encode(adminPassword))
                    .name("Super Admin")
                    .age(30)
                    .address("HQ")
                    .phone("+10000000000")
                    .employmentStatus("Active")
                    .role(adminRole)
                    .build();
            employeeRepository.save(admin);
        }
    }
}
