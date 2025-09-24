package com.exist.helpdesk.repository;

import com.exist.helpdesk.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    // Custom queries e.g. findByName(String name) can be added here
}
