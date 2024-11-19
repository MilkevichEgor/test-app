package com.fusion.service.dto;

import java.util.Set;

public record UserDto(Long id, String username, String password, Set<RoleDto> roles, DepartmentDto department) {
}