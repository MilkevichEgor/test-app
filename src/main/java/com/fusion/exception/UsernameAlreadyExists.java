package com.fusion.exception;

public class UsernameAlreadyExists extends RuntimeException {
  public UsernameAlreadyExists(String message) {
	super(message);
  }
}
