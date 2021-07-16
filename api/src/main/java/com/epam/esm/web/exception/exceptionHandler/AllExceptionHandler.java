package com.epam.esm.web.exception.exceptionHandler;

import com.epam.esm.exception.ApplicationException;
import com.epam.esm.exception.DuplicateResourceException;
import com.epam.esm.exception.PaginationException;
import com.epam.esm.exception.ResourceIsUsedException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.web.exception.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class AllExceptionHandler {

  @ExceptionHandler(value = Exception.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public @ResponseBody ResponseError generaExceptionHandler(Exception e) {
    return new ResponseError(e.toString(), 400);
  }

  @ExceptionHandler(value = {ValidationException.class, PaginationException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public @ResponseBody ResponseError validationError(ApplicationException e) {
    return new ResponseError(e.getMessage(), e.getErrorCode());
  }

  @ExceptionHandler(value = ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public @ResponseBody ResponseError resourceDoesNotExist(ApplicationException e) {
    return new ResponseError(e.getMessage(), e.getErrorCode());
  }

  @ExceptionHandler(value = ServiceException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public @ResponseBody ResponseError internalError(ApplicationException e) {
    return new ResponseError(e.getMessage(), e.getErrorCode());
  }

  @ExceptionHandler(value = {DuplicateResourceException.class, ResourceIsUsedException.class})
  @ResponseStatus(HttpStatus.CONFLICT)
  public @ResponseBody ResponseError conflictExceptionHandler(ApplicationException e) {
    return new ResponseError(e.getMessage(), e.getErrorCode());
  }
}
