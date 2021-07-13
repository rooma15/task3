package com.epam.esm.web.contoller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.web.OrderService;
import com.epam.esm.web.UserService;
import com.epam.esm.web.hateoas.HATHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/users")
public class UserController {

  private final UserService userService;
  private final OrderService orderService;
  private final HATHelper hatHelper;

  @Autowired
  public UserController(UserService userService, OrderService orderService, HATHelper hatHelper) {
    this.userService = userService;
    this.orderService = orderService;
    this.hatHelper = hatHelper;
  }

  @RequestMapping(method = GET, produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public CollectionModel<UserDto> getUsers(
      @RequestParam(required = false, defaultValue = "1") int page,
      @RequestParam(required = false, defaultValue = "5") int size) {
    List<UserDto> users = userService.getPaginated(page, size);
    return hatHelper.makeUserLinks(users);
  }

  @RequestMapping(value = "/{id}", method = GET, produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public UserDto getUser(@PathVariable int id) {
    UserDto user = userService.getById(id);
    return hatHelper.makeUserLinks(user);
  }

  @RequestMapping(
      value = "/{userId}/orders",
      method = POST,
      produces = "application/json",
      consumes = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public OrderDto orderCertificate(@PathVariable int userId, @RequestBody OrderDto order) {
    OrderDto newOrder = orderService.orderCertificate(userId, order.getCertificate().getId());
    return hatHelper.makeOrderLinks(newOrder, userId);
  }

  @RequestMapping(value = "/{userId}/orders", method = GET, produces = "application/json")
  public CollectionModel<OrderDto> getUserOrders(
      @PathVariable int userId,
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(required = false, defaultValue = "5") Integer size) {
    List<OrderDto> orders =
        orderService.getPaginated(orderService.getUserOrders(userId), page, size);
    return hatHelper.makeOrderLinks(orders, userId);
  }

  @RequestMapping(value = "/{userId}/orders/{orderId}", method = GET, produces = "application/json")
  public OrderDto getUserOrder(@PathVariable int userId, @PathVariable int orderId) {
    OrderDto order = orderService.getUserOrder(userId, orderId);
    return hatHelper.makeOrderLinks(order, userId);
  }

  @RequestMapping(value = "/most", method = GET)
  public TagDto getWidelyUsedTag() {
    TagDto mostWidelyUsedTag = userService.getMostWidelyUsedTagOfRichestUser();
    return hatHelper.makeTagLinks(mostWidelyUsedTag);
  }
}
