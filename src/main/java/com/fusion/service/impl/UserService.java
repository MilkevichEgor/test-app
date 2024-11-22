package com.fusion.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fusion.dto.UserDto;
import com.fusion.dto.request.ChangeRole;
import com.fusion.entity.Department;
import com.fusion.entity.Role;
import com.fusion.entity.User;
import com.fusion.exception.DepartmentNotFoundException;
import com.fusion.exception.RoleNotFoundException;
import com.fusion.exception.UserNotFoundException;
import com.fusion.exception.UsernameAlreadyExists;
import com.fusion.mapper.UserMapper;
import com.fusion.repository.DepartmentRepository;
import com.fusion.repository.RoleRepository;
import com.fusion.repository.UserRepository;
import com.fusion.service.IUserService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserService implements IUserService {

  private final RoleRepository roleRepository;
  private final UserRepository userRepository;
  private final DepartmentRepository departmentRepository;
  private final ObjectMapper objectMapper;
  private final UserMapper userMapper;

  @Transactional(readOnly = true)
  @Override
  public UserDto getOne(Long userId) {
	return userRepository.findById(userId)
		.map(userMapper::toDto).orElseThrow(() ->
			new UserNotFoundException("User with id `%s` not found".formatted(userId)));
  }

  @Transactional(readOnly = true)
  @Override
  public Page<UserDto> getList(Pageable pageable) {
	return userRepository.findAll(pageable)
		.map(userMapper::toDto);
  }

  @Transactional(readOnly = true)
  @Override
  public List<UserDto> getUsersInDepartment(String username) {
	User user = userRepository.findByUsername(username).orElseThrow(() ->
		new UserNotFoundException("User with username `%s` not found".formatted(username)));

	Department department = user.getDepartment();

	log.info(department.getTitle());

	return userRepository.findByDepartment(department)
		.stream()
		.map(userMapper::toDto)
		.toList();
  }

  @Transactional(readOnly = true)
  @Override
  public List<UserDto> getUsersInDepartment(Long departmentId) {
	return userRepository.findByDepartment_Id(departmentId)
		.stream()
		.map(userMapper::toDto)
		.toList();
  }

  @Transactional
  @Override
  public void create(UserDto userDto) {

	String newPassword = new BCryptPasswordEncoder().encode(userDto.password());
	User user = userMapper.toEntity(userDto);
	user.setPassword(newPassword);

	userRepository.save(user);
	log.info("User {} created", userDto.username());
  }

  @Transactional
  @Override
  public UserDto update(Long userId, JsonNode patchNode) throws IOException {

	String username = patchNode.has("username") ? patchNode.get("username").asText() : null;

	if (userRepository.existsByUsername(username)) {
	  log.error("Username {} already exists", username);
	  throw new UsernameAlreadyExists("Username '%s' already exists".formatted(username));
	}

	User user = userRepository.findById(userId).orElseThrow(() ->
		new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id `%s` not found".formatted(userId)));

	objectMapper.readerForUpdating(user).readValue(patchNode);

	return userMapper.toDto(userRepository.save(user));
  }

  @Transactional
  @Override
  public void delete(Long userId) {
	User user = userRepository.findById(userId).orElseThrow(() ->
		new UserNotFoundException("User with id `%s` not found".formatted(userId)));

	if (user != null) {
	  userRepository.delete(user);
	  log.info("User {} removed", userId);
	}
  }

  @Transactional
  @Override
  public void addRole(Long userId, ChangeRole roleName) {
	User user = userRepository.findById(userId).orElseThrow(() ->
		new UserNotFoundException("User with id `%s` not found".formatted(userId)));

	Role role = roleRepository.findByRoleName(roleName.role()).orElseThrow(() ->
		new RoleNotFoundException("Role not found"));

	if (user.getRoles().contains(role)) {
	  throw new ResponseStatusException(HttpStatus.CONFLICT, "Role already assigned to the user");
	}

	user.getRoles().add(role);
	userRepository.save(user);
	log.info("Role {} added", roleName);
  }

  @Transactional
  @Override
  public void removeRole(Long userId, ChangeRole roleName) {
	User user = userRepository.findById(userId).orElseThrow(() ->
		new UserNotFoundException("User with id `%s` not found".formatted(userId)));

	Role role = roleRepository.findByRoleName(roleName.role()).orElseThrow(() ->
		new RoleNotFoundException("Role not found"));

	if (!user.getRoles().contains(role)) {
	  log.error("Role {} not assigned to the user {}", roleName, userId);
	  throw new ResponseStatusException(HttpStatus.CONFLICT, "Role not assigned to the user");
	}

	user.getRoles().remove(role);
	userRepository.save(user);
	log.info("role {} deleted", roleName);
  }

  @Transactional
  @Override
  public void addUserInDepartment(Long userId, Long departmentId) {
	User user = userRepository.findById(userId).orElseThrow(() ->
		new UserNotFoundException("User with id `%s` not found".formatted(userId)));

	Department department = departmentRepository.findById(departmentId).orElseThrow(() ->
		new DepartmentNotFoundException("Department not found"));

	if (user.getDepartment().getTitle().contains(department.getTitle())) {
	  log.error("The user is in this department");
	  throw new ResponseStatusException(HttpStatus.CONFLICT, "The user is in this department");
	}
	user.setDepartment(department);
	userRepository.save(user);
	log.info("User {} assigned to department", userId);
  }

  @Override
  public UserDto findByUsername(String username) {
	return userRepository.findByUsername(username)
		.map(userMapper::toDto)
		.orElseThrow(() -> new UserNotFoundException("User not found"));
  }
}
