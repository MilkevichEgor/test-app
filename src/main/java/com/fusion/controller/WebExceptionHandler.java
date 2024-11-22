package com.fusion.controller;

import com.fusion.exception.DepartmentNotFoundException;
import com.fusion.exception.RoleNotFoundException;
import com.fusion.exception.UserNotFoundException;
import com.fusion.dto.response.ErrorResponse;
import com.fusion.exception.UserNotInDepartmentException;
import com.fusion.exception.UsernameAlreadyExists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WebExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
    ErrorResponse errorResponse = new ErrorResponse("NOT_FOUND", ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(DepartmentNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleDepartmentNotFoundException(DepartmentNotFoundException ex) {
    ErrorResponse errorResponse = new ErrorResponse("NOT_FOUND", ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(RoleNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleRoleNotFoundException(RoleNotFoundException ex) {
    ErrorResponse errorResponse = new ErrorResponse("NOT_FOUND", ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UsernameAlreadyExists.class)
  public ResponseEntity<ErrorResponse> handleUsernameAlreadyExists(UsernameAlreadyExists ex) {
    ErrorResponse errorResponse = new ErrorResponse("CONFLICT", ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(UserNotInDepartmentException.class)
  public ResponseEntity<ErrorResponse> handleUserNotInDepartmentException(UserNotInDepartmentException ex) {
    ErrorResponse errorResponse = new ErrorResponse("NOT_FOUND", ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }
}
