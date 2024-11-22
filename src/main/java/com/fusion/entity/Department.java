package com.fusion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "departments")
public class Department {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "title", unique = true)
  @NotNull(message = "Title must not be empty")
  private String title;

  @OneToMany(mappedBy = "department")
  @ToString.Exclude
  private List<User> userList;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "director_id", referencedColumnName = "id")
  @ToString.Exclude
  private User director;

  @Override
  public final boolean equals(Object o) {
	if (this == o) return true;
	if (o == null) return false;
	Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
	Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
	if (thisEffectiveClass != oEffectiveClass) return false;
	Department that = (Department) o;
	return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public final int hashCode() {
	return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }
}
