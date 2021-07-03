package com.epam.esm.exception;

public class ServiceException extends ApplicationException {
  public ServiceException() {}

  public ServiceException(String message) {
    super(message);
  }

  public ServiceException(String message, int errorCode) {
    super(message, errorCode);
  }
}
