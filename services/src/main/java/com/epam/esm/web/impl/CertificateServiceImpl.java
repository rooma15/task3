package com.epam.esm.web.impl;

import com.epam.esm.converter.CertificateConverter;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.filter.CertificateNameFilter;
import com.epam.esm.filter.DescriptionCertificateFilter;
import com.epam.esm.filter.FilterManager;
import com.epam.esm.filter.SortByDateFilter;
import com.epam.esm.filter.SortByDateNameFilter;
import com.epam.esm.filter.SortByNameFilter;
import com.epam.esm.filter.TagNameFilter;
import com.epam.esm.model.Certificate;
import com.epam.esm.validator.PartialValidator;
import com.epam.esm.validator.Validator;
import com.epam.esm.web.CertificateRepository;
import com.epam.esm.web.CertificateService;
import com.epam.esm.web.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl implements CertificateService {

  private final CertificateRepository certificateRepository;
  private final TagService tagService;
  private final Validator<CertificateDto> validator;
  private final PartialValidator partialValidator;

  /**
   * Instantiates a new Certificate service.
   *
   * @param certificateRepository the certificate repository
   * @param tagService the tag service
   * @param validator the validator
   */
  @Autowired
  public CertificateServiceImpl(
      CertificateRepository certificateRepository,
      TagService tagService,
      Validator<CertificateDto> validator,
      PartialValidator partialValidator) {
    this.tagService = tagService;
    this.certificateRepository = certificateRepository;
    this.validator = validator;
    this.partialValidator = partialValidator;
  }

  private final UnaryOperator<Certificate> saveOperator =
      new UnaryOperator<>() {
        @Override
        public Certificate apply(Certificate certificate) {
          return certificateRepository.create(certificate);
        }
      };

  private final UnaryOperator<Certificate> updateOperator =
      new UnaryOperator<>() {
        @Override
        public Certificate apply(Certificate certificate) {
          return certificateRepository.update(certificate);
        }
      };

  @Override
  public List<CertificateDto> getSortedCertificates(
      String tagName,
      String name,
      String description,
      String sortByDate,
      String sortByName,
      String sortByDateName) {
    FilterManager filterManager = new FilterManager(getAll());

    if (tagName != null) {
      filterManager.add(new TagNameFilter(tagName));
    }
    if (name != null) {
      filterManager.add(new CertificateNameFilter(name));
    }
    if (description != null) {
      filterManager.add(new DescriptionCertificateFilter(description));
    }
    if (sortByDate != null) {
      filterManager.add(new SortByDateFilter(sortByDate));
    }

    if (sortByName != null) {
      filterManager.add(new SortByNameFilter(sortByName));
    }
    if (sortByDateName != null) {
      filterManager.add(new SortByDateNameFilter(sortByDateName));
    }
    filterManager.start();
    return filterManager.getCertificates();
  }

  @Override
  public CertificateDto update(CertificateDto certificate, int id) {
    validator.validate(certificate);
    certificate.setId(id);
    certificate.setLastUpdateDate(LocalDateTime.now());
    Certificate updatedCertificate;
    try {
      updatedCertificate = saveOrUpdate(certificate, updateOperator);
    } catch (JpaObjectRetrievalFailureException e) {
      throw new ResourceNotFoundException("certificate with id = " + id + " does not exist", 40402);
    }
    return CertificateConverter.convertModelToDto(updatedCertificate);
  }

  @Override
  public CertificateDto partialUpdate(CertificateDto certificate, int id) {
    partialValidator.validate(certificate);
    certificate.setId(id);
    certificate.setLastUpdateDate(LocalDateTime.now());
    Certificate updatedCertificate;
    try {
      updatedCertificate = saveOrUpdate(certificate, updateOperator);
    } catch (JpaObjectRetrievalFailureException e) {
      throw new ResourceNotFoundException("certificate with id = " + id + " does not exist", 40402);
    }
    return CertificateConverter.convertModelToDto(updatedCertificate);
  }

  @Override
  public List<CertificateDto> getByTagName(String name) {
    return null;
  }

  private boolean checkTagExistence(Collection<TagDto> tags, TagDto tag) {
    for (TagDto tagDto : tags) {
      if (tag.getName().equals(tagDto.getName())) {
        return true;
      }
    }
    return false;
  }

  private Certificate saveOrUpdate(
      CertificateDto certificate, UnaryOperator<Certificate> operator) {
    List<TagDto> allTags = tagService.getAll();
    Set<TagDto> existedTags = new HashSet<>();
    if(certificate.getTags() != null){
      Set<TagDto> newTags =
              certificate.getTags().stream()
                      .filter(tag -> !checkTagExistence(allTags, tag))
                      .collect(Collectors.toSet());

      existedTags =
              allTags.stream()
                      .filter(tag -> checkTagExistence(certificate.getTags(), tag))
                      .collect(Collectors.toSet());

      certificate.setTags(newTags);
    }

    Certificate newCertificate =
        operator.apply(CertificateConverter.convertDtoToModel(certificate));

    certificate.setId(newCertificate.getId());

    if(certificate.getTags() != null){
      for (TagDto existedTag : existedTags) {
        certificateRepository.makeLink(certificate.getId(), existedTag.getId());
      }
    }
    certificateRepository.refresh(newCertificate);
    return newCertificate;
  }

  @Override
  public CertificateDto save(CertificateDto certificate) {
    validator.validate(certificate);
    certificate.setCreateDate(LocalDateTime.now());
    certificate.setLastUpdateDate(LocalDateTime.now());
    Certificate newCertificate = saveOrUpdate(certificate, saveOperator);
    return CertificateConverter.convertModelToDto(newCertificate);
  }

  @Override
  public List<CertificateDto> getAll() {
    return certificateRepository.findAll().stream()
        .map(CertificateConverter::convertModelToDto)
        .collect(Collectors.toList());
  }

  @Override
  public CertificateDto getById(int id) {
    Certificate certificate = certificateRepository.findOne(id);
    if (certificate == null) {
      throw new ResourceNotFoundException("certificate with id = " + id + " does not exist", 40402);
    }
    return CertificateConverter.convertModelToDto(certificateRepository.findOne(id));
  }

  @Override
  public int delete(int id) {
    try {
      certificateRepository.delete(id);
    } catch (JpaObjectRetrievalFailureException e) {
      throw new ResourceNotFoundException("certificate with id = " + id + " does not exist", 40402);
    }
    return 1;
  }
}
