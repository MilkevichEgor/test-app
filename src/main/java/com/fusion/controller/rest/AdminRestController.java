package com.fusion.controller.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fusion.dto.DepartmentDto;
import com.fusion.dto.UserDto;
import com.fusion.dto.request.ChangeDirectorRequest;
import com.fusion.dto.request.ChangeRole;
import com.fusion.service.impl.DepartmentService;
import com.fusion.service.IUserService;
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
  private final DepartmentService departmentService;

  @GetMapping
  public PagedModel<UserDto> getAllUsers(Pageable pageable) {
	Page<UserDto> users = userService.getList(pageable);
	return new PagedModel<>(users);
  }

  @GetMapping("/{id}")
  public UserDto getOne(@PathVariable Long id) {
	return userService.getOne(id);
  }

//  @PatchMapping("/users/{id}")
//  public UserDto updateUser(@PathVariable Long id, @RequestBody JsonNode patchNode) throws IOException {
//	return userService.update(id, patchNode);
//  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<String> delete(@PathVariable Long id) {
	userService.delete(id);
	return ResponseEntity.ok().body("User removed");
  }

  @PostMapping("/{userId}/roles")
  public ResponseEntity<String> addRole(@PathVariable Long userId, @RequestBody ChangeRole role) {
	userService.addRole(userId, role);
	return ResponseEntity.ok("Added role");
  }

  @DeleteMapping(path = {"/{userId}/roles"})
  public ResponseEntity<String> removeRole(@PathVariable Long userId, @RequestBody ChangeRole role) {
	userService.removeRole(userId, role);
	return ResponseEntity.ok("Role removed");
  }

  @PatchMapping(path = {"department/{departmentId}/director"})
  public void changeDirectorInDepartment(@PathVariable Long departmentId,
										 @RequestBody @Valid ChangeDirectorRequest userId) {
	departmentService.changeDirector(departmentId, userId);
  }

  @PostMapping(path = {"/department/{departmentId}/user/{userId}"})
  public void addUserInDepartment(@PathVariable Long departmentId, @PathVariable Long userId) {
	userService.addUserInDepartment(departmentId, userId);
  }

  @PostMapping("/department")
  public void createDepartment(@RequestBody DepartmentDto department) {
	departmentService.create(department);
  }

  @PatchMapping("/department/{id}")
  public DepartmentDto updateDepartment(@PathVariable Long id,
										@RequestBody JsonNode patchNode) throws IOException {
	return departmentService.update(id, patchNode);
  }

  @GetMapping("/department")
  public PagedModel<DepartmentDto> getAllDepartments(Pageable pageable) {
	Page<DepartmentDto> departments = departmentService.getList(pageable);
	return new PagedModel<>(departments);
  }

  @DeleteMapping("department/{id}")
  public ResponseEntity<String> deleteDepartment(@PathVariable Long id) {
	departmentService.delete(id);
	return ResponseEntity.ok().body("Department removed");
  }
}
