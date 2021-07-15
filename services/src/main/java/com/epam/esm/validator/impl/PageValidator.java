package com.epam.esm.validator.impl;

import com.epam.esm.exception.PaginationException;
import com.epam.esm.localization.LocaleTranslator;
import org.springframework.stereotype.Component;

@Component
public class PageValidator implements com.epam.esm.validator.PageValidator {

  private String translatedException = "";

  @Override
  public void validate(Integer page, Integer size) {
    if (page < 1) {
      translatedException = String.format(LocaleTranslator.translate("page.greaterThan"), 1);
      throw new PaginationException(translatedException, 403);
    }
    if (size < 0) {
      translatedException = String.format(LocaleTranslator.translate("pageSize.greaterThan"), 0);
      throw new PaginationException(translatedException, 403);
    }
  }
}
