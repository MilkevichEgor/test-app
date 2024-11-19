package com.fusion.service.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fusion.model.exception.UserNotFoundException;
import com.fusion.service.constant.UserRole;
import com.fusion.service.dto.RoleDto;
import com.fusion.service.dto.UserDto;
import com.fusion.service.entity.Department;
import com.fusion.service.entity.Role;
import com.fusion.service.exception.RoleNotFoundException;
import com.fusion.service.mapper.RoleMapper;
import com.fusion.service.repository.RoleRepository;
import com.fusion.service.entity.User;
import com.fusion.service.mapper.UserMapper;
import com.fusion.service.repository.UserRepository;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserService implements IUserService {

  private final RoleRepository roleRepository;
  private final UserRepository userRepository;
  private final ObjectMapper objectMapper;
  private final UserMapper userMapper;

  @Transactional(readOnly = true)
  @Override
  public UserDto getOne(Long id) {
	return userMapper.toDto(userRepository.findById(id).orElseThrow(() ->
		new UserNotFoundException("User with id `%s` not found".formatted(id))));
  }

  @Transactional(readOnly = true)
  @Override
  public Page<UserDto> getList(Pageable pageable) {
	return userRepository.findAll(pageable).map(userMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public List<UserDto> getUsersInDepartment(String username) {
	User user = userRepository.findByUsername(username).orElseThrow(() ->
		new UserNotFoundException("user not found"));

	Department department = user.getDepartment();

	log.info(department.getTitle());

	return userRepository.findByDepartment(department)
		.stream()
		.map(userMapper::toDto)
		.toList();
  }

  @Transactional
  @Override
  public void create(UserDto user) {
	userRepository.save(userMapper.toEntity(user));
  }

  @Transactional
  @Override
  public UserDto patch(Long id, JsonNode patchNode) throws IOException {
	User user = userRepository.findById(id).orElseThrow(() ->
		new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

	objectMapper.readerForUpdating(user).readValue(patchNode);

	return userMapper.toDto(userRepository.save(user));
  }

  @Transactional
  @Override
  public void delete(Long id) {
	User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user not found"));
	if (user != null) {
	  userRepository.delete(user);
	}
  }

  @Transactional
  @Override
  public void addRole(Long id, UserRole roleName) {
	User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user not found"));

	Role role = roleRepository.findByRoleName(roleName).orElseThrow(() -> new RoleNotFoundException("role not found"));

	if (user.getRoles().contains(role)) {
	  throw new ResponseStatusException(HttpStatus.CONFLICT, "Role already assigned to the user");
	}

	user.getRoles().add(role);
	userRepository.save(user);
	log.info("role {} added", roleName);
  }

  @Transactional
  @Override
  public void removeRole(Long id, UserRole roleName) {
	User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user not found"));

	Role role = roleRepository.findByRoleName(roleName).orElseThrow(() -> new RoleNotFoundException("role not found"));

	if (!user.getRoles().contains(role)) {
	  throw new ResponseStatusException(HttpStatus.CONFLICT, "Role not assigned to the user");
	}

	user.getRoles().remove(role);
	userRepository.save(user);
	log.info("role {} deleted", roleName);
  }
}
