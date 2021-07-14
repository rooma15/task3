package com.epam.esm.web;

import com.epam.esm.dto.CertificateDto;

import java.util.List;

public interface CertificateService extends Service<CertificateDto> {

  CertificateDto partialUpdate(CertificateDto certificate, int id);

  /**
   * Update certificate in db
   *
   * @param certificate the certificate with new values of the fields
   * @return {@link CertificateDto} of new certificate
   */
  CertificateDto fullUpdate(CertificateDto certificate, int id);

  List<CertificateDto> getPaginated(Integer page, Integer size);

  /**
   * Save entity in db
   *
   * @param EntityDto the entity dto
   * @return the t
   */
  CertificateDto save(CertificateDto EntityDto);

  /**
   * Delete entoty from db
   *
   * @param id the id
   * @return 1 if enerything was okay, 0 otherwise
   */
  int delete(int id);

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
      int page,
      int size);
}
