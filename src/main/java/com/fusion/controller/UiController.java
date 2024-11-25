package com.fusion.controller;

import static com.fusion.constant.UserRole.ROLE_ADMIN;
import static com.fusion.constant.UserRole.ROLE_DIRECTOR;

import com.fusion.constant.UserRole;
import com.fusion.dto.DepartmentDto;
import com.fusion.dto.RoleDto;
import com.fusion.dto.UserDto;
import com.fusion.service.IDepartmentService;
import com.fusion.service.IUserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class UiController {

  private final IDepartmentService departmentService;
  private final IUserService userService;

  @ModelAttribute("userContext")
  public UserDto userContext(Authentication authentication) {

	List<RoleDto> roles = authentication.getAuthorities().stream()
		.map(GrantedAuthority::getAuthority)
		.map(authority -> new RoleDto(null, UserRole.valueOf(authority)))
		.toList();

	return UserDto.builder()
		.username(authentication.getName())
		.roles(roles)
		.build();
  }

  @GetMapping(path = {"/"})
  public String getIndexHtml(Model model, Authentication authentication) {
	List<DepartmentDto> list = departmentService.getDepartmentByRole(authentication.getName());
	model.addAttribute("departmentList", list);
	return "pages/departmentList";
  }

  @GetMapping(path = {"/department/{id}"})
  public String getDepartmentEmployees(@PathVariable Long id, Model model) {
	DepartmentDto department = departmentService.getOne(id);
	List<UserDto> userList = userService.getUsersInDepartment(id);

	model.addAttribute("departmentData", department);
	model.addAttribute("userList", userList);

	return "pages/department";
  }

  @GetMapping(path = {"/users/edit/{id}"})
  public String editUser(@PathVariable Long id, Model model, Authentication authentication) {
	String username = authentication.getName();
	UserDto currentUser = userService.findByUsername(username);

	UserDto profileUser = userService.getOne(id);
	DepartmentDto departmentDto = profileUser.department();
	List<RoleDto> currentRoles = currentUser.roles();

	boolean canEdit = currentUser.id().equals(profileUser.id()) ||
		currentRoles.stream().anyMatch(role -> role.role() == ROLE_ADMIN) ||
		currentRoles.stream().anyMatch(role -> role.role() == ROLE_DIRECTOR);

	model.addAttribute("profileUser", profileUser);
	model.addAttribute("departmentData", departmentDto);
	model.addAttribute("currentUser", currentUser);
	model.addAttribute("canEdit", canEdit);

	return "pages/profile";
  }

  @DeleteMapping(path = {"/users/delete/{userId}"})
  public String getEmployees(@PathVariable Long userId) {
	userService.delete(userId);

	return "pages/profile";
  }
}

