package com.fusion.mapper;

import com.fusion.dto.DepartmentDto;
import com.fusion.entity.Department;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DepartmentMapper {
  Department toEntity(DepartmentDto departmentDto);

  @Mapping(target = "directorId", source = "director.id")
  DepartmentDto toDto(Department department);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Department partialUpdate(DepartmentDto departmentDto, @MappingTarget Department department);
}