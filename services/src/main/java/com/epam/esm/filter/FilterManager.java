package com.epam.esm.filter;

import com.epam.esm.dto.CertificateDto;

import java.util.List;

public class FilterManager {
  private Filter head;
  private Filter current;
  private int size;

  private List<CertificateDto> certificates;

  /**
   * Instantiates a new Filter manager.
   *
   * @param certificates the certificates
   */
  public FilterManager(List<CertificateDto> certificates) {
    this.certificates = certificates;
  }

  /**
   * Add filter to queue of filters
   *
   * @param newFilter the new filter
   */
  public void add(Filter newFilter) {
    if (size == 0) {
      head = newFilter;
      current = newFilter;
    } else {
      current.next = newFilter;
      current = current.next;
    }
    size++;
  }
  /**
   * Start filtering list of certificates with number of filters that were added by {@link
   * FilterManager#add(Filter)}
   */
  public void start() {
    if (head != null) {
      certificates = head.filter(certificates);
    }
  }

  /**
   * Gets certificates.
   *
   * @return {@link List} of certificates
   */
  public List<CertificateDto> getCertificates() {
    return certificates;
  }
}
