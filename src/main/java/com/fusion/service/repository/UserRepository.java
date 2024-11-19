package com.fusion.service.repository;

import com.fusion.service.entity.Department;
import com.fusion.service.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//  @Query("SELECT u FROM User u JOIN FETCH u.department WHERE u.department = :department")
  List<User> findByDepartment(Department department);
  Optional<User> findByUsername(String username);
}