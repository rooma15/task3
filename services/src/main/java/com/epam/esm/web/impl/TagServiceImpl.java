package com.epam.esm.web.impl;

import com.epam.esm.converter.TagConverter;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.DuplicateResourceException;
import com.epam.esm.exception.PaginationException;
import com.epam.esm.exception.ResourceIsUsedException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.localization.LocaleTranslator;
import com.epam.esm.model.Tag;
import com.epam.esm.validator.PageValidator;
import com.epam.esm.validator.Validator;
import com.epam.esm.web.TagRepository;
import com.epam.esm.web.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

  private final TagRepository tagRepository;

  private final String CHECK_TAG_EXISTENCE =
      "select count(certificate_id) from certificateTags where tag_id=?";

  private final Validator<TagDto> tagDtoValidator;
  private final PageValidator pageValidator;

  @Autowired
  public TagServiceImpl(
      TagRepository tagRepository, Validator<TagDto> validator, PageValidator pageValidator) {
    this.tagRepository = tagRepository;
    this.tagDtoValidator = validator;
    this.pageValidator = pageValidator;
  }

  @Override
  @Transactional
  public TagDto save(TagDto tag) {
    tagDtoValidator.validate(tag);
    List<TagDto> tags = getAll();
    for (TagDto tagDto : tags) {
      if (tagDto.getName().equals(tag.getName())) {
        String formattedException =
            String.format(
                LocaleTranslator.translate("tag.alreadyExists"), tag.getName());
        throw new DuplicateResourceException(formattedException, 40901);
      }
    }
    Tag newTag = tagRepository.create(TagConverter.convertDtoToModel(tag));
    tag.setId(newTag.getId());
    return tag;
  }

  @Override
  @Transactional
  public List<TagDto> getAll() {
    return tagRepository.findAll().stream()
        .map(TagConverter::convertModelToDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public TagDto getById(int id) {
    Tag tag = tagRepository.findOne(id);
    if (tag == null) {
      String formattedException =
              String.format(
                      LocaleTranslator.translate("tag.doesNotExist"), id);
      throw new ResourceNotFoundException(formattedException, 40401);
    }
    return TagConverter.convertModelToDto(tag);
  }

  @Override
  @Transactional
  public int delete(int id) {
    if (!isResourceExist(id)) {
      String formattedException =
              String.format(
                      LocaleTranslator.translate("tag.doesNotExist"), id);
      throw new ResourceNotFoundException(formattedException, 40401);
    } else {
      List<Object> params = new ArrayList<>();
      params.add(id);
      BigInteger tagCounter =
          (BigInteger) tagRepository.doNativeGetQuery(CHECK_TAG_EXISTENCE, params);
      if (tagCounter.intValue() > 0) {
        String formattedException =
                String.format(
                        LocaleTranslator.translate("tag.inUse"), id);
        throw new ResourceIsUsedException(formattedException, 40901);
      } else {
        tagRepository.delete(id);
      }
    }
    return 1;
  }

  @Override
  @Transactional
  public List<TagDto> getPaginated(Integer page, Integer size) {
    pageValidator.validate(page, size);
    int from = (page - 1) * size;
    List<TagDto> tags =
        tagRepository.getPaginated(from, size).stream()
            .map(TagConverter::convertModelToDto)
            .collect(Collectors.toList());
    if (tags.isEmpty()) {
      throw new PaginationException(LocaleTranslator.translate("page.doesNotExist"), 404);
    }
    return tags;
  }
}
