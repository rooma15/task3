package com.epam.esm.web.impl;

import com.epam.esm.converter.UserConverter;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.PaginationException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.localization.LocaleTranslator;
import com.epam.esm.model.User;
import com.epam.esm.validator.PageValidator;
import com.epam.esm.web.CertificateService;
import com.epam.esm.web.TagService;
import com.epam.esm.web.UserRepository;
import com.epam.esm.web.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final CertificateService certificateService;
  private final TagService tagService;
  private final PageValidator pageValidator;
  private final String WIDELY_USED_TAG =
          """
                  select count(tag.id) as kol, tag.id from tag 
                  join certificate_tags on tag.id=certificate_tags.tag_id
                  join orders on certificate_tags.certificate_id=orders.certificate_id
                  join (select max(orders.cost) as summary, user.id as id from user 
                  join orders on user.id=orders.user_id group by user.id
                  order by summary desc
                  limit 1) as usar
                  where user_id=usar.id group by(tag.id)
                  order by kol desc
                  limit 1""";

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository,
      CertificateService certificateService,
      TagService tagService,
      PageValidator pageValidator) {
    this.userRepository = userRepository;
    this.certificateService = certificateService;
    this.tagService = tagService;
    this.pageValidator = pageValidator;
  }

  @Override
  @Transactional
  public List<UserDto> getAll() {
    return userRepository.findAll().stream()
        .map(UserConverter::convertModelToDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public UserDto getById(int id) {
    User user = userRepository.findOne(id);
    if (user == null) {
      String formattedException =
              String.format(
                      LocaleTranslator.translate("user.doesNotExist"), id);
      throw new ResourceNotFoundException(formattedException, 40404);
    }
    return UserConverter.convertModelToDto(user);
  }


  @Override
  @Transactional
  public TagDto getMostWidelyUsedTagOfRichestUser() {
    List<Object> params = new ArrayList<>();
    int tagId = userRepository.findMostUsedTag(WIDELY_USED_TAG, params);

    return tagService.getById(tagId);
  }

  @Override
  @Transactional
  public List<UserDto> getPaginated(Integer page, Integer size) {
    pageValidator.validate(page, size);
    int from = (page - 1) * size;
    List<UserDto> users =
            userRepository.getPaginated(from, size).stream()
                    .map(UserConverter::convertModelToDto)
                    .collect(Collectors.toList());
    if (users.isEmpty()) {
      throw new PaginationException("page.doesNotExist", 404);
    }
    return users;
  }
}
