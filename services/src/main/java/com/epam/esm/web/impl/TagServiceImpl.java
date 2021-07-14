package com.epam.esm.web.impl;

import com.epam.esm.converter.TagConverter;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.DuplicateResourceException;
import com.epam.esm.exception.PaginationException;
import com.epam.esm.exception.ResourceIsUsedException;
import com.epam.esm.exception.ResourceNotFoundException;
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
        throw new DuplicateResourceException(
            "tag with name = " + tag.getName() + " already exists", 40901);
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
  public TagDto getById(int id) {
    Tag tag = tagRepository.findOne(id);
    if (tag == null) {
      throw new ResourceNotFoundException("tag with id = " + id + " does not exist", 40401);
    }
    return TagConverter.convertModelToDto(tag);
  }

  @Override
  public int delete(int id) {
    if (!isResourceExist(id)) {
      throw new ResourceNotFoundException("certificate with id = " + id + " does not exist", 40402);
    } else {
      List<Object> params = new ArrayList<>();
      params.add(id);
      BigInteger tagCounter =
          (BigInteger) tagRepository.doNativeGetQuery(CHECK_TAG_EXISTENCE, params);
      if (tagCounter.intValue() > 0) {
        throw new ResourceIsUsedException("tag with id = " + id + " is in use", 40901);
      } else {
        tagRepository.delete(id);
      }
    }
    return 1;
  }

  @Override
  public List<TagDto> getPaginated(Integer page, Integer size) {
    pageValidator.validate(page, size);
    int from = (page - 1) * size;
    List<TagDto> tags =
        tagRepository.getPaginated(from, size).stream()
            .map(TagConverter::convertModelToDto)
            .collect(Collectors.toList());
    if (tags.isEmpty()) {
      throw new PaginationException("this page does not exist", 404);
    }
    return tags;
  }
}
