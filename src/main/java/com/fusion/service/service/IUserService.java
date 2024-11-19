package com.fusion.service.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fusion.service.constant.UserRole;
import com.fusion.service.dto.RoleDto;
import com.fusion.service.dto.UserDto;
import com.fusion.service.entity.User;
import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {

  UserDto getOne(Long id);

  Page<UserDto> getList(Pageable pageable);

  List<UserDto> getUsersInDepartment(String username);

  void create(UserDto user);

  UserDto patch(Long id, JsonNode patchNode) throws IOException;

  void delete(Long id);

  void addRole(Long id, UserRole role);

  void removeRole(Long id, UserRole roleName);
}

