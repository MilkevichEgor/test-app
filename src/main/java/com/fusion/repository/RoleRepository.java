package com.fusion.repository;

import com.fusion.constant.UserRole;
import com.fusion.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByRoleName(UserRole roleName);
}