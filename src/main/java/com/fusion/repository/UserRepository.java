package com.fusion.repository;

import com.fusion.entity.Department;
import com.fusion.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  List<User> findByDepartment(Department department);

  Optional<User> findByUsername(String username);

  boolean existsByUsername(String username);

  List<User> findByDepartment_Id(Long id);
}