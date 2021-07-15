package com.epam.esm.validator.impl;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.localization.LocaleTranslator;
import com.epam.esm.validator.PartialValidator;
import com.epam.esm.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PartialCertificateValidator implements PartialValidator {

  private Validator<TagDto> tagValidator;

  private final int MIN_NAME_LENGTH = 3;
  private final int MAX_NAME_LENGTH = 20;
  private final int MIN_DESCRIPTION_LENGTH = 10;
  private final int MAX_DESCRIPTION_LENGTH = 120;

  private String translatedException = "";

  @Autowired
  @Qualifier("tagValidator")
  public void setTagDtoValidator(Validator<TagDto> tagValidator) {
    this.tagValidator = tagValidator;
  }

  public boolean validate(CertificateDto certificate) {
    if (certificate.getName() != null) {
      if (certificate.getName().length() < MIN_NAME_LENGTH) {
        translatedException =
            String.format(
                LocaleTranslator.translate("certificate.nameLengthLowerBorder"), MIN_NAME_LENGTH);
        throw new ValidationException(translatedException, 40302);
      }
      if (certificate.getName().length() > MAX_NAME_LENGTH) {
        translatedException =
            String.format(
                LocaleTranslator.translate("certificate.nameLengthUpperBorder"), MAX_NAME_LENGTH);
        throw new ValidationException(translatedException, 40302);
      }
    }

    if (certificate.getDescription() != null) {
      if (certificate.getDescription().length() < MIN_DESCRIPTION_LENGTH) {
        translatedException =
            String.format(
                LocaleTranslator.translate("certificate.descriptionLengthLowerBorder"),
                MIN_DESCRIPTION_LENGTH);
        throw new ValidationException(translatedException, 40302);
      }
      if (certificate.getDescription().length() > MAX_DESCRIPTION_LENGTH) {
        translatedException =
            String.format(
                LocaleTranslator.translate("certificate.descriptionLengthUpperBorder"),
                MAX_DESCRIPTION_LENGTH);
        throw new ValidationException(translatedException, 40302);
      }
    }

    if (certificate.getPrice() != null) {
      if (certificate.getPrice().doubleValue() < 0) {
        translatedException = String.format(LocaleTranslator.translate("certificate.priceGreaterThan"), 0);
        throw new ValidationException(translatedException, 40302);
      }
    }
    if (certificate.getDuration() != null) {
      if (certificate.getDuration() < 0) {
        translatedException = String.format(LocaleTranslator.translate("certificate.durationGreaterThan"), 0);
        throw new ValidationException(translatedException, 40302);
      }
    }
    if (certificate.getTags() != null) {
      certificate.getTags().forEach(tagValidator::validate);
    }
    return true;
  }
}
