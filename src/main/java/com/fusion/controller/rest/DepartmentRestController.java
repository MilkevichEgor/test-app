package com.fusion.controller.rest;

import com.fusion.dto.DepartmentDto;
import com.fusion.service.IDepartmentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/departments"})
@RequiredArgsConstructor
public class DepartmentRestController {

  private final IDepartmentService departmentService;

  @GetMapping("/all")
  public PagedModel<DepartmentDto> getList(Pageable pageable) {
	Page<DepartmentDto> departments = departmentService.getList(pageable);
	return new PagedModel<>(departments);
  }

  @GetMapping(path = {"/role"})
  public List<DepartmentDto> getDepartmentByRole(Authentication authentication) {
	String currentUser = authentication.getName();
	return departmentService.getDepartmentByRole(currentUser);
  }

  @PreAuthorize("hasRole('ADMIN', 'DIRECTOR')")
  @DeleteMapping(path = {"/{departmentId}/user/{userId}"})
  public void deleteUserFromDepartment(@PathVariable Long departmentId, @PathVariable Long userId) {
	departmentService.deleteUserFromDepartment(departmentId, userId);
  }
}
