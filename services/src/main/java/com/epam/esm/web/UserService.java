package com.epam.esm.web;

import com.epam.esm.dto.UserDto;

import java.util.List;

public interface UserService {
  List<UserDto> getAll();

  UserDto getById(int id);

  UserDto orderCertificate(int userId, int certificateId);
}
