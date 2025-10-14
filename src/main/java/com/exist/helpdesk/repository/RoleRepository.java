package com.exist.helpdesk.repository;

import com.exist.helpdesk.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

    @Query("SELECT r FROM Role r")
    List<Role> findAllRoles();

    @Query("SELECT r FROM Role r WHERE r.name = :name")
    List<Role> findByName(@Param("name") String name);

    @Query("SELECT r FROM Role r WHERE r.id > :minId")
    List<Role> findByIdGreaterThan(@Param("minId") Long minId);

    @Query("SELECT r.id FROM Role r WHERE r.name LIKE :pattern")
    List<Long> findRoleIdsByPattern(@Param("pattern") String pattern);

    @Query("SELECT r FROM Role r WHERE r.version = :version")
    List<Role> findByVersion(@Param("version") Integer version);

    @Query("SELECT r FROM Role r WHERE r.name = :name AND r.version = :version")
    List<Role> findByNameAndVersion(@Param("name") String name, @Param("version") Integer version);
}
