package com.fusion.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fusion.dto.DepartmentDto;
import com.fusion.dto.request.ChangeDirectorRequest;
import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDepartmentService {
  DepartmentDto getOne(Long id);

  void create(DepartmentDto department);

  DepartmentDto update(Long id, JsonNode patchNode) throws IOException;

  Page<DepartmentDto> getList(Pageable pageable);

  List<DepartmentDto> getList();

  List<DepartmentDto> getDepartmentByRole(String username);

  void delete(Long id);

  void changeDirector(Long departmentId, ChangeDirectorRequest userId);

  void deleteUserFromDepartment(Long departmentId, Long userId);
}
