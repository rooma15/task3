package com.epam.esm.validator.impl;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.validator.PartialValidator;
import com.epam.esm.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PartialCertificateValidator implements PartialValidator {

  private Validator<TagDto> tagValidator;

  private final int MIN_NAME_LENGTH = 3;
  private final int MAX_NAME_LENGTH = 20;
  private final int MIN_DESCRIPTION_LENGTH = 10;
  private final int MAX_DESCRIPTION_LENGTH = 120;

  @Autowired
  @Qualifier("tagValidator")
  public void setTagDtoValidator(Validator<TagDto> tagValidator) {
    this.tagValidator = tagValidator;
  }

  public boolean validate(
      CertificateDto certificate, StringBuilder sql, Map<String, Object> paramsMap) {
    if (certificate.getName() != null) {
      if (certificate.getName().length() < MIN_NAME_LENGTH) {
        throw new ValidationException("Name length must be more than 3 symbols", 40302);
      }
      if (certificate.getName().length() > MAX_NAME_LENGTH) {
        throw new ValidationException("Name length must be  below 20 symbols", 40302);
      }
      sql.append("name=:name,");
      paramsMap.put("name", certificate.getName());
    }
    if (certificate.getDescription() != null) {
      if (certificate.getDescription().length() < MIN_DESCRIPTION_LENGTH) {
        throw new ValidationException("description length must be more than 10 symbols", 40302);
      }
      if (certificate.getDescription().length() > MAX_DESCRIPTION_LENGTH) {
        throw new ValidationException("description length must be below 120 symbols", 40302);
      }
      sql.append("description=:description,");
      paramsMap.put("description", certificate.getDescription());
    }
    if (certificate.getPrice() != null) {
      if (certificate.getPrice().doubleValue() < 0) {
        throw new ValidationException("price must be more than 0 or equals 0", 40302);
      }
      sql.append("price=:price,");
      paramsMap.put("price", certificate.getPrice());
    }
    if (certificate.getDuration() != null) {
      if (certificate.getDuration() < 0) {
        throw new ValidationException("duration must be more than 0 or equals 0", 40302);
      }
      sql.append("duration=:duration,");
      paramsMap.put("duration", certificate.getDuration());
    }
    if (certificate.getTags() != null) {
      certificate.getTags().forEach(tagValidator::validate);
    }
    return true;
  }
}
