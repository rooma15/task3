package com.epam.esm.validator.impl;

import com.epam.esm.exception.PaginationException;
import org.springframework.stereotype.Component;

@Component
public class PageValidator implements com.epam.esm.validator.PageValidator {

  @Override
  public void validate(Integer page, Integer size) {
    if (page < 1) {
      throw new PaginationException("page is invalid", 403);
    }
    if (size < 0) {
      throw new PaginationException("size must be more or equals 0 ", 403);
    }
  }
}
