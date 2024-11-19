package com.fusion.service.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fusion.service.constant.UserRole;
import com.fusion.service.dto.UserDto;
import com.fusion.service.mapper.UserMapper;
import com.fusion.service.service.IUserService;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminRestController {

  private final IUserService userService;
//  private final UserMapper userMapper;

  @GetMapping
  public PagedModel<UserDto> getList(Pageable pageable) {
	Page<UserDto> users = userService.getList(pageable);
	return new PagedModel<>(users);
  }

  @GetMapping("/{id}")
  public UserDto getOne(@PathVariable Long id) {
	return userService.getOne(id);
  }

  @PostMapping
  public void create(@RequestBody @Valid UserDto user) {
	userService.create(user);
  }

  @PatchMapping("/{id}")
  public UserDto patch(@PathVariable Long id, @RequestBody JsonNode patchNode) throws IOException {
	return userService.patch(id, patchNode);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
	userService.delete(id);
  }

  @PostMapping("/{id}/roles")
  public void addRole(@PathVariable Long id, @RequestBody UserRole role) {
	userService.addRole(id, role);
	ResponseEntity.ok("Added role");
  }

  @DeleteMapping(path = {"/{id}/roles"})
  public void removeRole(@PathVariable Long id, @RequestBody UserRole role) {
	userService.removeRole(id, role);
	ResponseEntity.ok("Role deleted");
  }
}
