package com.fusion.entity;

import com.fusion.constant.UserRole;
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
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, unique = true)
  UserRole roleName;

  @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
  List<User> users;

  @Override
  public final boolean equals(Object o) {
	if (this == o) return true;
	if (o == null) return false;
	Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
	Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
	if (thisEffectiveClass != oEffectiveClass) return false;
	Role role = (Role) o;
	return getId() != null && Objects.equals(getId(), role.getId());
  }

  @Override
  public final int hashCode() {
	return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }
}