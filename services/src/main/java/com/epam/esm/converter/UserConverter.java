package com.epam.esm.converter;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.model.Order;
import com.epam.esm.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {
  public static User convertDtoToModel(UserDto userDto) {
    List<Order> orders =
        userDto.getOrders().stream()
            .map(OrderConverter::convertDtoToModel)
            .collect(Collectors.toList());
    return new User(userDto.getId(), userDto.getFirstName(), userDto.getLastName(), orders);
  }

  public static UserDto convertModelToDto(User user) {
    List<OrderDto> orders =
        user.getOrders().stream()
            .map(OrderConverter::convertModelToDto)
            .collect(Collectors.toList());
    return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), orders);
  }
}
