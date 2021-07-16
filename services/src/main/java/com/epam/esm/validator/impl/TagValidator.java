package com.epam.esm.validator.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.localization.LocaleTranslator;
import com.epam.esm.validator.Validator;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component("tagValidator")
public class TagValidator implements Validator<TagDto> {

  private final int MIN_NAME_LENGTH = 2;
  private final int MAX_NAME_LENGTH = 20;
  private final String SPACE_REGEX = "\\s";

  private String translatedException = "";

  @Override
  public boolean validate(TagDto tag) {
    if (Pattern.matches(SPACE_REGEX, tag.getName())) {
      translatedException = LocaleTranslator.translate("tag.oneWordConstraint");
      throw new ValidationException(translatedException, 40301);
    }
    if (tag.getName().length() <= MIN_NAME_LENGTH) {
      translatedException =
          String.format(LocaleTranslator.translate("tag.nameLengthLowerBorder"), MIN_NAME_LENGTH);
      throw new ValidationException(translatedException, 40301);
    }
    if (tag.getName().length() > MAX_NAME_LENGTH) {
      translatedException =
          String.format(LocaleTranslator.translate("tag.nameLengthUpperBorder"), MAX_NAME_LENGTH);
      throw new ValidationException(translatedException, 40301);
    }
    return true;
  }
}
