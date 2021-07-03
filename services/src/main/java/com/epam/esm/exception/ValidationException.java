package com.epam.esm.exception;

public class ValidationException extends ApplicationException {

  public ValidationException() {}

  public ValidationException(String message) {
    super(message);
  }

  public ValidationException(String message, int errorCode) {
    super(message, errorCode);
  }
}
