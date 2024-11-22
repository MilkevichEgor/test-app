package com.fusion.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fusion.constant.UserRole;
import com.fusion.dto.DepartmentDto;
import com.fusion.dto.request.ChangeDirectorRequest;
import com.fusion.entity.Department;
import com.fusion.entity.Role;
import com.fusion.entity.User;
import com.fusion.exception.DepartmentNotFoundException;
import com.fusion.exception.RoleNotFoundException;
import com.fusion.exception.UserNotFoundException;
import com.fusion.exception.UserNotInDepartmentException;
import com.fusion.mapper.DepartmentMapper;
import com.fusion.repository.DepartmentRepository;
import com.fusion.repository.RoleRepository;
import com.fusion.repository.UserRepository;
import com.fusion.service.IDepartmentService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Log4j2
@RequiredArgsConstructor
@Service
public class DepartmentService implements IDepartmentService {

  private final DepartmentRepository departmentRepository;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final DepartmentMapper departmentMapper;
  private final ObjectMapper objectMapper;

  @Transactional(readOnly = true)
  @Override
  public DepartmentDto getOne(Long id) {
	return departmentMapper.toDto(departmentRepository.findById(id)
		.orElseThrow(() -> new DepartmentNotFoundException("Department with id `%s` not found".formatted(id))));
  }

  @Transactional
  @Override
  public void create(DepartmentDto department) {
	departmentRepository.save(departmentMapper.toEntity(department));
	log.info("Department {} created", department.title());
  }

  @Transactional
  @Override
  public DepartmentDto update(Long id, JsonNode patchNode) throws IOException {
	Department department = departmentRepository.findById(id).orElseThrow(() ->
		new ResponseStatusException(HttpStatus.NOT_FOUND, "Department with id `%s` not found".formatted(id)));

	objectMapper.readerForUpdating(department).readValue(patchNode);

	return departmentMapper.toDto(departmentRepository.save(department));
  }

  @Transactional(readOnly = true)
  @Override
  public Page<DepartmentDto> getList(Pageable pageable) {
	return departmentRepository.findAll(pageable)
		.map(departmentMapper::toDto);
  }

  @Override
  public List<DepartmentDto> getList() {
	return departmentRepository.findAll().stream().map(departmentMapper::toDto).toList();
  }

  @Override
  public List<DepartmentDto> getDepartmentByRole(String username) {
	User currentUser = userRepository.findByUsername(username).orElseThrow(() ->
		new UserNotFoundException("User with username `%s` not found".formatted(username)));

	List<Role> currentRoles = currentUser.getRoles();

	if (currentRoles.stream().anyMatch(role -> role.getRoleName() == UserRole.ROLE_USER) ||
		currentRoles.stream().anyMatch(role -> role.getRoleName() == UserRole.ROLE_DIRECTOR)) {
	  return departmentRepository.findById(currentUser.getDepartment().getId())
		  .map(departmentMapper::toDto)
		  .stream()
		  .toList();

	} else if (currentRoles.stream().anyMatch(role -> role.getRoleName() == UserRole.ROLE_ADMIN)) {
	  return departmentRepository.findAll()
		  .stream()
		  .map(departmentMapper::toDto)
		  .toList();
	} else {
	  throw new AccessDeniedException("Access denied for user with roles: " + currentRoles);
	}

  }

  @Transactional
  @Override
  public void delete(Long id) {
	Department department = departmentRepository.findById(id).orElseThrow(() ->
		new DepartmentNotFoundException("Department with id `%s` not found".formatted(id)));
	if (department != null) {
	  departmentRepository.delete(department);
	  log.info("Department {} removed", id);
	}
  }

  @Transactional
  @Override
  public void changeDirector(Long departmentId, ChangeDirectorRequest request) {
	Department department = departmentRepository.findById(departmentId).orElseThrow(() ->
		new DepartmentNotFoundException("Department with id `%s` not found".formatted(departmentId)));

	User newDirector = userRepository.findById(request.userId()).orElseThrow(() ->
		new UserNotFoundException("User with id `%s` not found".formatted(request.userId())));

	Role directorRole = roleRepository.findByRoleName(UserRole.ROLE_DIRECTOR).orElseThrow(() ->
		new RoleNotFoundException("Role not found"));

	if (department.getDirector() != null) {
	  User currentDirector = department.getDirector();
	  currentDirector.getRoles().remove(directorRole);
	  userRepository.save(currentDirector);
	}

	department.setDirector(newDirector);
	newDirector.getRoles().add(directorRole);
	userRepository.save(newDirector);

	departmentRepository.save(department);
	log.info("Director changed");
  }

  @Transactional
  @Override
  public void deleteUserFromDepartment(Long departmentId, Long userId) {
	User user = userRepository.findById(userId).orElseThrow(() ->
		new UserNotFoundException("User with id `%s` not found".formatted(userId)));

	Department department = departmentRepository.findById(departmentId).orElseThrow(() ->
		new DepartmentNotFoundException("Department with id `%s` not found".formatted(departmentId)));

	if (user.getDepartment() == null) {
	  throw new UserNotInDepartmentException("User is not assigned to this department");
	}

	if (!user.getDepartment().equals(department)) {
	  throw new UserNotInDepartmentException("User is not assigned to this department");
	}

	user.setDepartment(null);

	userRepository.save(user);
	log.info("User {} deleted from department", user.getUsername());
  }
}
