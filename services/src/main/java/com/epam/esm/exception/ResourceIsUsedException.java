package com.epam.esm.exception;

public class ResourceIsUsedException extends ApplicationException {
  public ResourceIsUsedException() {
    super();
  }

  public ResourceIsUsedException(String message) {
    super(message);
  }

  public ResourceIsUsedException(String message, int errorCode) {
    super(message, errorCode);
  }
}
