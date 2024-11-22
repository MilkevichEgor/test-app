package com.fusion.mapper;

import com.fusion.dto.UserDto;
import com.fusion.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {RoleMapper.class})
public interface UserMapper {
  User toEntity(UserDto userDto);

  UserDto toDto(User user);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  User partialUpdate(UserDto userDto, @MappingTarget User user);
}