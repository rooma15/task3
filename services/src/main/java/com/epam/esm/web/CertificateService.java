package com.epam.esm.web;

import com.epam.esm.dto.CertificateDto;

import java.util.List;

public interface CertificateService extends Service<CertificateDto> {

  /**
   * Partitial update certificate dto
   *
   * @param id the id
   * @return the certificate dto
   */
  CertificateDto partialUpdate(CertificateDto certificate, int id);

  /**
   * Update certificate in db
   *
   * @param certificate the certificate with new values of the fields
   * @return {@link CertificateDto} of new certificate
   */
  CertificateDto update(CertificateDto certificate, int id);

  /**
   * Gets certificates by tag name.
   *
   * @param name the name
   * @return {@link List} of certificates with certain tag
   */
  List<CertificateDto> getByTagName(String name);

  /**
   * Gets sorted certificates.
   *
   * @param tagName the tag name
   * @param name the name
   * @param description the description
   * @param sortByName the sort by name
   * @param sortByDate the sort by date
   * @return the sorted certificates
   */
  List<CertificateDto> getSortedCertificates(
      String tagName,
      String name,
      String description,
      String sortByName,
      String sortByDate,
      String sortByDateName);
}
