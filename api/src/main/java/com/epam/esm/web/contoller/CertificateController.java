package com.epam.esm.web.contoller;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.web.CertificateService;
import com.epam.esm.web.hateoas.HATHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

  private final CertificateService certificateService;
  private final HATHelper hatHelper;
  /**
   * Instantiates a new Certificate controller.
   *
   * @param certificateService the certificate service
   */
  @Autowired
  public CertificateController(CertificateService certificateService, HATHelper hatHelper) {
    this.certificateService = certificateService;
    this.hatHelper = hatHelper;
  }

  /**
   * Gets certificates.
   *
   * @param tagName the tag name
   * @param name the name
   * @param description the description
   * @param sortByDate the sort by date
   * @param sortByName the sort by name
   * @return the certificates
   */
  @RequestMapping(method = GET, produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public CollectionModel<CertificateDto> getCertificates(
      @RequestParam(required = false) String tagName,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String description,
      @RequestParam(required = false) String sortByDate,
      @RequestParam(required = false) String sortByName,
      @RequestParam(required = false) String sortByDateName,
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(required = false, defaultValue = "5") Integer size) {
    List<CertificateDto> certificates =
        certificateService.getSortedCertificates(
            tagName, name, description, sortByDate, sortByName, sortByDateName, page, size);
    return hatHelper.makeCertificateLinks(certificates);
  }

  /**
   * Get certificate certificate dto.
   *
   * @param id the id of the certificate
   * @return the certificate dto
   */
  @RequestMapping(value = "/{id}", method = GET, produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public CertificateDto getCertificate(@PathVariable int id) {
    CertificateDto certificate = certificateService.getById(id);
    return hatHelper.makeCertificateLinks(certificate);
  }

  /**
   * Update certificate dto.
   *
   * @param id the id of the certificate
   * @param certificate the certificate to be updated
   * @return the certificate dto
   */
  @RequestMapping(
      value = "/{id}",
      method = PUT,
      consumes = "application/json",
      produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public CertificateDto update(@PathVariable int id, @RequestBody CertificateDto certificate) {
    CertificateDto updatedCertificate = certificateService.fullUpdate(certificate, id);
    return hatHelper.makeCertificateLinks(updatedCertificate);
  }

  /**
   * Update part certificate dto.
   *
   * @param id the id of the certificate
   * @param certificate certificate dto made from request params
   * @return the certificate dto
   */
  @RequestMapping(
      value = "/{id}",
      method = PATCH,
      consumes = "application/json",
      produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public CertificateDto updatePart(@PathVariable int id, @RequestBody CertificateDto certificate) {
    CertificateDto updatedCertificate = certificateService.partialUpdate(certificate, id);
    return hatHelper.makeCertificateLinks(updatedCertificate);
  }

  /**
   * Create certificate dto.
   *
   * @param certificate the certificate
   * @return the certificate dto
   */
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(method = POST, consumes = "application/json", produces = "application/json")
  public CertificateDto create(@RequestBody CertificateDto certificate) {
    CertificateDto newCertificate = certificateService.save(certificate);
    return hatHelper.makeCertificateLinks(newCertificate);
  }

  /**
   * Delete certificate.
   *
   * @param id the id of the certificate
   */
  @RequestMapping(value = "/{id}", method = DELETE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable int id) {
    certificateService.delete(id);
  }
}
