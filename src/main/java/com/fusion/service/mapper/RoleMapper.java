package com.fusion.service.mapper;

import com.fusion.service.dto.RoleDto;
import com.fusion.service.entity.Role;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {
  Role toEntity(RoleDto roleDto);

  @Mapping(target = "role", source = "roleName")
  RoleDto toDto(Role role);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Role partialUpdate(RoleDto roleDto, @MappingTarget Role role);
}