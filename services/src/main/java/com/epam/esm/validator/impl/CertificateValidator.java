package com.epam.esm.validator.impl;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.localization.LocaleTranslator;
import com.epam.esm.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
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

  private String translatedException = "";

  @Override
  public boolean validate(CertificateDto certificate) {

    validateName(certificate);

    validateDescription(certificate);

    validatePrice(certificate);

    validateDuration(certificate);

    validateTags(certificate);
    return true;
  }

  private void validateTags(CertificateDto certificate) {
    if (certificate.getTags() == null) {
      translatedException = LocaleTranslator.translate("tag.mustBeFilled");
      throw new ValidationException(translatedException, 40302);
    }
    certificate.getTags().forEach(tagDtoValidator::validate);
  }

  private void validateName(CertificateDto certificate) {
    if (certificate.getName() == null) {
      translatedException = LocaleTranslator.translate("certificate.nameMustBeFilled");
      throw new ValidationException(translatedException, 40302);
    }
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

  private void validateDescription(CertificateDto certificate) {
    if (certificate.getDescription() == null) {
      translatedException = LocaleTranslator.translate("certificate.descriptionMustBeFilled");
      throw new ValidationException(translatedException, 40302);
    }
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

  private void validateDuration(CertificateDto certificate) {
    if (certificate.getDuration() == null) {
      translatedException = LocaleTranslator.translate("certificate.durationMustBeFilled");
      throw new ValidationException(translatedException, 40302);
    }
    if (certificate.getDuration() < 0) {
      translatedException = String.format(LocaleTranslator.translate("certificate.durationGreaterThan"), 0);
      throw new ValidationException(translatedException, 40302);
    }
  }

  private void validatePrice(CertificateDto certificate) {
    if (certificate.getPrice() == null) {
      translatedException = LocaleTranslator.translate("certificate.priceMustBeFilled");
      throw new ValidationException(translatedException, 40302);
    }
    if (certificate.getPrice().doubleValue() < 0) {
      translatedException = String.format(LocaleTranslator.translate("certificate.priceGreaterThan"), 0);
      throw new ValidationException(translatedException, 40302);
    }
  }
}
