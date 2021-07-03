package com.epam.esm.validator;

import com.epam.esm.dto.CertificateDto;

import java.util.Map;

public interface PartialValidator {
  boolean validate(CertificateDto certificate, StringBuilder sql, Map<String, Object> paramsMap);
}
