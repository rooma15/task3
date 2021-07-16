package com.epam.esm.exception;

public class PaginationException extends ApplicationException {
  public PaginationException() {}

  public PaginationException(String message) {
    super(message);
  }

  public PaginationException(String message, int errorCode) {
    super(message, errorCode);
  }
}
