package com.fusion.dto;

import jakarta.persistence.Transient;
import java.util.List;
import lombok.Builder;

@Builder
public record UserDto(Long id, String username, @Transient String password,
					  String firstName, String lastName, List<RoleDto> roles, DepartmentDto department) {
}