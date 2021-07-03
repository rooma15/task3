package com.epam.esm.web.impl;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.filter.CertificateNameFilter;
import com.epam.esm.filter.DescriptionCertificateFilter;
import com.epam.esm.filter.FilterManager;
import com.epam.esm.filter.SortByDateFilter;
import com.epam.esm.filter.SortByDateNameFilter;
import com.epam.esm.filter.SortByNameFilter;
import com.epam.esm.filter.TagNameFilter;
import com.epam.esm.model.Certificate;
import com.epam.esm.converter.CertificateConverter;
import com.epam.esm.validator.PartialValidator;
import com.epam.esm.validator.Validator;
import com.epam.esm.web.CertificateRepository;
import com.epam.esm.web.CertificateService;
import com.epam.esm.web.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl implements CertificateService {

  private final CertificateRepository certificateRepository;
  private final TagService tagService;
  private final Validator<CertificateDto> validator;
  private final PartialValidator partialCertificateValidator;

  /**
   * Instantiates a new Certificate service.
   *
   * @param certificateRepository the certificate repository
   * @param tagService the tag service
   * @param validator the validator
   * @param partialCertificateValidator the partial certificate validator
   */
  @Autowired
  public CertificateServiceImpl(
      CertificateRepository certificateRepository,
      TagService tagService,
      Validator<CertificateDto> validator,
      PartialValidator partialCertificateValidator) {
    this.tagService = tagService;
    this.certificateRepository = certificateRepository;
    this.validator = validator;
    this.partialCertificateValidator = partialCertificateValidator;
  }

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

  private <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
    Set<Object> seen = ConcurrentHashMap.newKeySet();
    return elem -> seen.add(keyExtractor.apply(elem));
  }

  @Transactional
  public List<CertificateDto> getByTagName(String name) {
    int tagId = tagService.getByTagName(name).getId();
    List<Certificate> certificateModels = certificateRepository.findCertificatesByTagId(tagId);
    return certificateModels.stream()
        .map(
            certificate ->
                CertificateConverter.convertModelToDto(
                    certificate, tagService.getTagsByCertificateId(certificate.getId())))
        .collect(Collectors.toList());
  }

  private List<TagDto> createNonExistentTags(CertificateDto certificateDto) {
    List<TagDto> tagDtos = certificateDto.getTags();
    List<TagDto> distinctTags = new ArrayList<>();

    if (tagDtos != null) {
      distinctTags =
          tagDtos.stream().filter(distinctByKey(TagDto::getName)).collect(Collectors.toList());
      List<TagDto> existingTags = tagService.getAll();
      List<TagDto> newTags =
          distinctTags.stream()
              .filter(
                  tag ->
                      existingTags.stream()
                          .noneMatch(distinctTag -> tag.getName().equals(distinctTag.getName())))
              .collect(Collectors.toList());
      newTags.forEach(tagService::save);
    }
    return distinctTags;
  }

  @Override
  @Transactional
  public CertificateDto save(CertificateDto certificateDto) {
    validator.validate(certificateDto);

    certificateDto.setCreateDate(LocalDateTime.now());
    certificateDto.setLastUpdateDate(LocalDateTime.now());

    List<TagDto> distinctTags = createNonExistentTags(certificateDto);

    Certificate certificate = CertificateConverter.convertDtoToModel(certificateDto);
    int lastId = certificateRepository.create(certificate);
    if (lastId != 0) {
      List<TagDto> currentCertificateTags =
          distinctTags.stream()
              .map(tag -> tagService.getByTagName(tag.getName()))
              .collect(Collectors.toList());
      currentCertificateTags.forEach(tag -> certificateRepository.makeLink(lastId, tag.getId()));
      return CertificateConverter.convertModelToDto(
          certificateRepository.findOne(lastId), currentCertificateTags);
    } else {
      throw new ServiceException(
          "Something went wrong during creation of certificate on server", 50002);
    }
  }

  @Override
  @Transactional
  public List<CertificateDto> getAll() {
    return certificateRepository.findAll().stream()
        .map(
            model ->
                CertificateConverter.convertModelToDto(
                    model, tagService.getTagsByCertificateId(model.getId())))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public CertificateDto getById(int id) {
    try {
      return CertificateConverter.convertModelToDto(
          certificateRepository.findOne(id), tagService.getTagsByCertificateId(id));
    } catch (EmptyResultDataAccessException e) {
      throw new ResourceNotFoundException("certificate with id=[" + id + "] does not exist", 40402);
    }
  }

  @Override
  @Transactional
  public int delete(int id) {
    if (isResourceExist(id)) {
      certificateRepository.deleteLink(id);
      return certificateRepository.delete(id);
    } else {
      throw new ResourceNotFoundException("certificate with id=[" + id + "] does not exist", 40402);
    }
  }

  private void reconnectCertificateTags(CertificateDto certificate) {
    List<TagDto> currentCertificateTags;
    List<TagDto> tagsToBeUpdated = createNonExistentTags(certificate);
    currentCertificateTags =
        tagsToBeUpdated.stream()
            .map(tag -> tagService.getByTagName(tag.getName()))
            .collect(Collectors.toList());
    certificateRepository.deleteLink(certificate.getId());
    currentCertificateTags.forEach(
        tag -> certificateRepository.makeLink(certificate.getId(), tag.getId()));
  }

  @Override
  @Transactional
  public CertificateDto partialUpdate(CertificateDto certificate, int id) {
    certificate.setId(id);
    Map<String, Object> paramsMap = new HashMap<>();
    StringBuilder sql = new StringBuilder();
    sql.append("update certificate set ");
    partialCertificateValidator.validate(certificate, sql, paramsMap);
    sql.append("last_update_date=:last_update_date");
    sql.append(" where id=:id;");
    paramsMap.put("last_update_date", LocalDateTime.now());
    paramsMap.put("id", id);
    if (certificate.getTags() != null) {
      reconnectCertificateTags(certificate);
    }
    Certificate finalCertificate;
    if (certificateRepository.update(sql.toString(), paramsMap, id) > 0) {
      finalCertificate = certificateRepository.findOne(id);
    } else {
      throw new ServiceException(
          "something went wrong on server while updating certificate id=" + id + ";", 50002);
    }
    List<TagDto> certTags = tagService.getTagsByCertificateId(id);
    return CertificateConverter.convertModelToDto(finalCertificate, certTags);
  }

  @Override
  @Transactional
  public CertificateDto update(CertificateDto certificate, int id) {
    certificate.setId(id);
    validator.validate(certificate);
    List<TagDto> tagsToBeUpdated = createNonExistentTags(certificate);
    List<TagDto> currentCertificateTags =
        tagsToBeUpdated.stream()
            .map(tag -> tagService.getByTagName(tag.getName()))
            .collect(Collectors.toList());

    CertificateDto existedCertificate = getById(certificate.getId());
    certificate.setCreateDate(existedCertificate.getCreateDate());
    certificate.setLastUpdateDate(LocalDateTime.now());
    certificateRepository.deleteLink(certificate.getId());
    currentCertificateTags.forEach(
        tag -> certificateRepository.makeLink(certificate.getId(), tag.getId()));
    Certificate newModel;
    if (certificateRepository.update(CertificateConverter.convertDtoToModel(certificate)) > 0) {
      newModel = certificateRepository.findOne(certificate.getId());
    } else {
      throw new ServiceException(
          "something went wrong on server while updating certificate id="
              + certificate.getId()
              + ";",
          50002);
    }
    return CertificateConverter.convertModelToDto(newModel, currentCertificateTags);
  }
}
