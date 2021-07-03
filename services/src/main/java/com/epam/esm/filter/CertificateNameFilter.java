package com.epam.esm.filter;

import com.epam.esm.dto.CertificateDto;

import java.util.List;
import java.util.stream.Collectors;

public class CertificateNameFilter extends Filter {

  public CertificateNameFilter(String param) {
    super(param);
  }

  @Override
  List<CertificateDto> filter(List<CertificateDto> soughtList) {
    soughtList =
        soughtList.stream()
            .filter(certificate -> certificate.getName().contains(param))
            .collect(Collectors.toList());
    return next == null
            ? soughtList
            : next.filter(soughtList);
  }
}
