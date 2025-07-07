package com.example.callcenter1.repository.operator;

import com.example.callcenter1.model.operator.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleId(Integer roleId);
    Optional<Role> findByRoleType(String roleType);
} 