package com.epam.esm.web;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;

import java.util.List;

public interface UserService extends Service<UserDto> {
  List<UserDto> getAll();

  UserDto getById(int id);

  TagDto getMostWidelyUsedTagOfRichestUser();

  List<UserDto> getPaginated(Integer page, Integer size);
}
