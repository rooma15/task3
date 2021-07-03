package com.epam.esm.web.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.DuplicateResourceException;
import com.epam.esm.exception.ResourceIsUsedException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.Tag;
import com.epam.esm.converter.TagConverter;
import com.epam.esm.validator.Validator;
import com.epam.esm.web.TagRepository;
import com.epam.esm.web.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

  private final TagRepository tagRepository;

  private final Validator<TagDto> tagDtoValidator;

  @Autowired
  public TagServiceImpl(TagRepository tagRepository, Validator<TagDto> validator) {
    this.tagRepository = tagRepository;
    this.tagDtoValidator = validator;
  }

  @Override
  @Transactional
  public TagDto save(TagDto tagDto) {
    if (isResourceExist(tagDto.getName())) {
      throw new DuplicateResourceException(
          "Resource with name = [" + tagDto.getName() + "] already exists", 40901);
    }
    tagDtoValidator.validate(tagDto);
    Tag tag = TagConverter.convertDtoToModel(tagDto);
    int lastId = tagRepository.create(tag);
    if (lastId != 0) {
      return getById(lastId);
    } else {
      throw new ServiceException("Something went wrong while creating tag on server", 50001);
    }
  }

  @Override
  public boolean isResourceExist(String name) {
    try {
      getByTagName(name);
      return true;
    } catch (ResourceNotFoundException e) {
      return false;
    }
  }

  @Override
  public List<TagDto> getAll() {
    return tagRepository.findAll().stream()
        .map(TagConverter::convertModelToDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public int delete(int id) {
    if (isResourceExist(id)) {
      if (tagRepository.isTagConnected(id)) {
        throw new ResourceIsUsedException(
            "This tag is connected with existing certificates", 40301);
      }
      return tagRepository.delete(id);
    } else {
      throw new ResourceNotFoundException("Tag with id = " + id + " does not exist", 40401);
    }
  }

  @Override
  public TagDto getById(int id) {
    try {
      return TagConverter.convertModelToDto(tagRepository.findOne(id));
    } catch (EmptyResultDataAccessException e) {
      throw new ResourceNotFoundException("Tag with id = " + id + " does not exist", 40401);
    }
  }

  public TagDto getByTagName(String name) {
    try {
      return TagConverter.convertModelToDto(tagRepository.findByName(name));
    } catch (EmptyResultDataAccessException e) {
      throw new ResourceNotFoundException("Tag with name = " + name + " does not exist", 40401);
    }
  }

  @Override
  public List<TagDto> getTagsByCertificateId(int certId) {
    return tagRepository.findTagsByCertificateId(certId).stream()
        .map(TagConverter::convertModelToDto)
        .collect(Collectors.toList());
  }
}
