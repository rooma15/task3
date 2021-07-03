package com.epam.esm.web.contoller;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.web.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
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

  /**
   * Instantiates a new Certificate controller.
   *
   * @param certificateService the certificate service
   */
  @Autowired
  public CertificateController(CertificateService certificateService) {
    this.certificateService = certificateService;
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
  public List<CertificateDto> getCertificates(
      @RequestParam(required = false) String tagName,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String description,
      @RequestParam(required = false) String sortByDate,
      @RequestParam(required = false) String sortByName,
      @RequestParam(required = false) String sortByDateName) {

    return certificateService.getSortedCertificates(
        tagName, name, description, sortByDate, sortByName, sortByDateName);
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
    return certificateService.getById(id);
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
    return certificateService.update(certificate, id);
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
    return certificateService.partialUpdate(certificate, id);
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
    return certificateService.save(certificate);
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
