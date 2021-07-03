package com.epam.esm.validator.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.validator.Validator;
import org.springframework.stereotype.Component;

@Component("tagValidator")
public class TagValidator implements Validator<TagDto> {

  private final int MIN_NAME_LENGTH = 2;
  private final int MAX_NAME_LENGTH = 20;

  @Override
  public boolean validate(TagDto tag) {
    if (tag.getName().length() <= MIN_NAME_LENGTH) {
      throw new ValidationException("Tag length must be more than 2 characters", 40301);
    }
    if (tag.getName().length() > MAX_NAME_LENGTH) {
      throw new ValidationException("Tag length must me below 20 characters", 40301);
    }
    return true;
  }
}
