package com.epam.esm.validator.impl;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("certificateValidator")
public class CertificateValidator implements Validator<CertificateDto> {

  private final Validator<TagDto> tagDtoValidator;

  @Autowired
  public CertificateValidator(Validator<TagDto> tagDtoValidator) {
    this.tagDtoValidator = tagDtoValidator;
  }

  private final int MIN_NAME_LENGTH = 3;
  private final int MAX_NAME_LENGTH = 20;
  private final int MIN_DESCRIPTION_LENGTH = 10;
  private final int MAX_DESCRIPTION_LENGTH = 120;

  @Override
  public boolean validate(CertificateDto certificate) {

    if (certificate.getName() == null) {
      throw new ValidationException("Name must be filled", 40302);
    }
    if (certificate.getName().length() < MIN_NAME_LENGTH) {
      throw new ValidationException("Name length must be more than 3 symbols", 40302);
    }
    if (certificate.getName().length() > MAX_NAME_LENGTH) {
      throw new ValidationException("Name length must be  below 20 symbols", 40302);
    }

    if (certificate.getDescription() == null) {
      throw new ValidationException("description must be filled", 40302);
    }
    if (certificate.getDescription().length() < MIN_DESCRIPTION_LENGTH) {
      throw new ValidationException("description length must be more than 10 symbols", 40302);
    }
    if (certificate.getDescription().length() > MAX_DESCRIPTION_LENGTH) {
      throw new ValidationException("description length must be below 120 symbols", 40302);
    }

    if (certificate.getPrice() == null) {
      throw new ValidationException("price must be filled", 40302);
    }
    if (certificate.getPrice().doubleValue() < 0) {
      throw new ValidationException("price must be more than 0 or equals 0", 40302);
    }

    if (certificate.getDuration() == null) {
      throw new ValidationException("duration must be filled", 40302);
    }
    if (certificate.getDuration() < 0) {
      throw new ValidationException("duration must be more than 0 or equals 0", 40302);
    }

    if (certificate.getTags() == null) {
      throw new ValidationException("tags must be filled", 40302);
    }
    certificate.getTags().forEach(tagDtoValidator::validate);
    return true;
  }
}
