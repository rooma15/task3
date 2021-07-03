package com.epam.esm.exception;

public class DuplicateResourceException extends ApplicationException {
  public DuplicateResourceException() {}

  public DuplicateResourceException(String message) {
    super(message);
  }

  public DuplicateResourceException(String message, int errorCode) {
    super(message, errorCode);
  }
}
