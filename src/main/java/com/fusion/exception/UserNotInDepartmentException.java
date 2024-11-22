package com.fusion.exception;

public class UserNotInDepartmentException extends RuntimeException {
  public UserNotInDepartmentException(String message) {
	super(message);
  }
}
