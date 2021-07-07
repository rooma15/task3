package com.epam.esm.web.impl;

import com.epam.esm.converter.OrderConverter;
import com.epam.esm.converter.UserConverter;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.DuplicateResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.User;
import com.epam.esm.web.CertificateService;
import com.epam.esm.web.UserRepository;
import com.epam.esm.web.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final CertificateService certificateService;

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository,
      CertificateService certificateService) {
    this.userRepository = userRepository;
    this.certificateService = certificateService;
  }

  @Override
  public List<UserDto> getAll() {
    return userRepository.findAll().stream()
        .map(UserConverter::convertModelToDto)
        .collect(Collectors.toList());
  }

  @Override
  public UserDto getById(int id) {
    User user = userRepository.findOne(id);
    if (user == null) {
      throw new ResourceNotFoundException("user with id = " + id + " does not exist", 40402);
    }
    return UserConverter.convertModelToDto(user);
  }

  @Override
  public UserDto orderCertificate(int userId, int certificateId) {
    CertificateDto certificate = certificateService.getById(certificateId);
    OrderDto order = new OrderDto(LocalDateTime.now(), certificate.getPrice(), certificate);
    try {
      return UserConverter.convertModelToDto(
          userRepository.order(userId, OrderConverter.convertDtoToModel(order)));
    } catch (JpaSystemException e) {
      throw new DuplicateResourceException(
          "user with id = "
              + userId
              + " has already ordered certificate with id  = "
              + certificateId,
          40902);
    }
  }
}
