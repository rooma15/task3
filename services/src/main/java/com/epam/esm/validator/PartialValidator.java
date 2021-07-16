package com.epam.esm.validator;

import com.epam.esm.dto.CertificateDto;

public interface PartialValidator {
  boolean validate(CertificateDto certificate);
}
