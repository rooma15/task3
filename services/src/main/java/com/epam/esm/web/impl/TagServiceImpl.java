package com.epam.esm.web.impl;

import com.epam.esm.converter.TagConverter;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.DuplicateResourceException;
import com.epam.esm.exception.ResourceIsUsedException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.Tag;
import com.epam.esm.validator.Validator;
import com.epam.esm.web.TagRepository;
import com.epam.esm.web.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

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
  public TagDto save(TagDto tag) {
    tagDtoValidator.validate(tag);
    try{
      Tag newTag = tagRepository.create(TagConverter.convertDtoToModel(tag));
      tag.setId(newTag.getId());
    }catch (DataIntegrityViolationException e){
      throw new DuplicateResourceException("tag with name = " + tag.getName() + " already exists", 40901);
    }
    return tag;
  }

  @Override
  public List<TagDto> getAll() {
    return tagRepository.findAll().stream()
        .map(TagConverter::convertModelToDto)
        .collect(Collectors.toList());
  }

  @Override
  public TagDto getById(int id) {
    Tag tag = tagRepository.findOne(id);
    if(tag == null){
      throw new ResourceNotFoundException("tag with id = " + id + " does not exist", 40401);
    }
    return TagConverter.convertModelToDto(tag);
  }

  @Override
  public int delete(int id) {
    try{
      tagRepository.delete(id);
    }catch (JpaObjectRetrievalFailureException e){
      throw new ResourceNotFoundException("tag with id = " + id + " does not exist", 40401);
    }
    catch (JpaSystemException ex){
      throw new ResourceIsUsedException("tag with id = " + id + " is in use", 40901);
    }
    return 1;
  }
}
