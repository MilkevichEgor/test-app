package com.fusion.service.mapper;

import com.fusion.service.dto.DepartmentDto;
import com.fusion.service.entity.Department;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DepartmentMapper {
  Department toEntity(DepartmentDto departmentDto);

  DepartmentDto toDto(Department department);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Department partialUpdate(DepartmentDto departmentDto, @MappingTarget Department department);
}