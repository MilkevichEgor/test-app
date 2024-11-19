package com.fusion.service.service;

import com.fusion.model.exception.DepartmentNotFoundException;
import com.fusion.service.dto.DepartmentDto;
import com.fusion.service.mapper.DepartmentMapper;
import com.fusion.service.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DepartmentService {

  private final DepartmentRepository departmentRepository;
  private final DepartmentMapper departmentMapper;

  public DepartmentDto findById(Long id) {
	return departmentMapper.toDto(departmentRepository.findById(id)
		.orElseThrow(() -> new DepartmentNotFoundException("department not found")));
  }
}
