package com.fusion.constant;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum UserRole {
  ROLE_USER, ROLE_ADMIN, ROLE_DIRECTOR
}