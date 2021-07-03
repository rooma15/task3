package com.epam.esm.filter;

import com.epam.esm.dto.CertificateDto;

import java.util.List;

public abstract class Filter {

  protected final String param;
  public Filter next;

  /**
   * Instantiates a new Filter.
   *
   * @param param the param
   */
  public Filter(String param) {
    this.param = param;
  }

  /**
   * filter list of certificates by certain criteria
   *
   * @param soughtList {@link List} of certificates
   * @return sorted list of certificates
   */
  abstract List<CertificateDto> filter(List<CertificateDto> soughtList);
}
