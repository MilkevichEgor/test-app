package com.fusion.service.entity;

import com.fusion.service.constant.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role {
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, unique = true)
  UserRole roleName;
  @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
  List<User> users;
}