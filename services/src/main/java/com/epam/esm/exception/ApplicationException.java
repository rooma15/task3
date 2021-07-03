package com.epam.esm.exception;

public class ApplicationException extends RuntimeException {

  protected int errorCode;

  public ApplicationException() {}

  public ApplicationException(String message) {
    super(message);
  }

  public ApplicationException(String message, int errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

  public int getErrorCode() {
    return errorCode;
  }
}
