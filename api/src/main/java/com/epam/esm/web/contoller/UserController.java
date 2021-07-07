package com.epam.esm.web.contoller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.web.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping(method = GET, produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public List<UserDto> getUsers() {
    return userService.getAll();
  }

  @RequestMapping(value = "/{id}", method = GET, produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public UserDto getUser(@PathVariable int id) {
    return userService.getById(id);
  }

  @RequestMapping(
      value = "/{userId}/orders",
      method = POST,
      produces = "application/json",
      consumes = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public UserDto orderCertificate(@PathVariable int userId, @RequestBody OrderDto order) {
    return userService.orderCertificate(userId, order.getCertificateId());
  }
}
