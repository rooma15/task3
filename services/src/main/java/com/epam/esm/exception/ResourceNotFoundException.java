package com.epam.esm.exception;

public class ResourceNotFoundException extends ApplicationException {
  public ResourceNotFoundException() {}

  public ResourceNotFoundException(String message) {
    super(message);
  }

  public ResourceNotFoundException(String message, int errorCode) {
    super(message, errorCode);
  }
}
