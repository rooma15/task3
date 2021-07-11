package com.epam.esm.web.contoller;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.model.Certificate;
import com.epam.esm.web.CertificateService;
import com.epam.esm.web.hateoas.HAT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

  private final CertificateService certificateService;
  private final HAT hat;
  /**
   * Instantiates a new Certificate controller.
   *
   * @param certificateService the certificate service
   */
  @Autowired
  public CertificateController(CertificateService certificateService, HAT hat) {
    this.certificateService = certificateService;
    this.hat = hat;
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
    /*Link selfLink = linkTo(CertificateController.class).withSelfRel();
    for (CertificateDto certificate : certificates) {
      Link link =
          linkTo(methodOn(CertificateController.class).getCertificate(certificate.getId()))
              .withSelfRel();
      certificate.add(link);
    }
    return CollectionModel.of(certificates, selfLink);*/
    HAT hat = new HAT();
    return hat.makeCertificateLinks(certificates, false);
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
    /*Link link =
        linkTo(methodOn(CertificateController.class).getCertificate(id))
            .withSelfRel();
    certificate.add(link);
    return certificate;*/
    return new ArrayList<>(
            hat.makeCertificateLinks(Arrays.asList(certificate), true).getContent())
        .get(0);
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
    return new ArrayList<>(
            hat.makeCertificateLinks(Arrays.asList(updatedCertificate), true).getContent())
            .get(0);
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
    return new ArrayList<>(
            hat.makeCertificateLinks(Arrays.asList(updatedCertificate), true).getContent())
            .get(0);
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
    /*Link link = linkTo(methodOn(CertificateController.class).create(certificate)).withSelfRel();
    newCertificate.add(link);
    return newCertificate;*/
    return new ArrayList<>(
            hat.makeCertificateLinks(Arrays.asList(newCertificate), true).getContent())
            .get(0);
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
