package com.fusion.model.exception;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String message) {
	super(message);
  }
}
