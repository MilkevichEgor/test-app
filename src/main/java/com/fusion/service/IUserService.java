package com.fusion.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fusion.dto.UserDto;
import com.fusion.dto.request.ChangeRole;
import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {

  UserDto getOne(Long id);

  Page<UserDto> getList(Pageable pageable);

  List<UserDto> getUsersInDepartment(String username);

  List<UserDto> getUsersInDepartment(Long userId);

  void create(UserDto user);

  UserDto update(Long id, JsonNode patchNode) throws IOException;

  void delete(Long id);

  void addRole(Long id, ChangeRole role);

  void removeRole(Long id, ChangeRole roleName);

  void addUserInDepartment(Long userId, Long departmentId);

  UserDto findByUsername(String username);
}

