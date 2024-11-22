package com.fusion.controller.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fusion.dto.UserDto;
import com.fusion.service.IUserService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {

  private final IUserService userService;

  @GetMapping(path = {"/{id}"})
  public UserDto getOne(@PathVariable Long id) {
    return userService.getOne(id);
  }

  @GetMapping(path = {"/all"})
  public PagedModel<UserDto> getList(Pageable pageable) {
	Page<UserDto> users = userService.getList(pageable);
	return new PagedModel<>(users);
  }

  @GetMapping("/departments")
  public List<UserDto> getUserInDepartment(Authentication authentication) {
	String username = authentication.getName();
	return userService.getUsersInDepartment(username);
  }

  @GetMapping("/departments/{id}")
  public List<UserDto> getUserInDepartment(@PathVariable Long id) {
	return userService.getUsersInDepartment(id);
  }

  @PatchMapping("/{id}")
  public UserDto updateUser(@PathVariable Long id, @RequestBody JsonNode patchNode) throws IOException {
	return userService.update(id, patchNode);
  }
}
