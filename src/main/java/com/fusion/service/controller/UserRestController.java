package com.fusion.service.controller;

import com.fusion.service.dto.UserDto;
import com.fusion.service.entity.User;
import com.fusion.service.service.IUserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {

  private final IUserService userService;

  @GetMapping(path = {"/{id}"})
  public ResponseEntity<UserDto> getOne(@PathVariable Long id) {
    return ResponseEntity.ok(userService.getOne(id));
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
}
