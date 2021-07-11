package com.epam.esm.web.contoller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.web.OrderService;
import com.epam.esm.web.UserService;
import com.epam.esm.web.hateoas.HAT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/users")
public class UserController {

  private final UserService userService;
  private final OrderService orderService;
  private final HAT hat;

  @Autowired
  public UserController(UserService userService, OrderService orderService, HAT hat) {
    this.userService = userService;
    this.orderService = orderService;
    this.hat = hat;
  }

  @RequestMapping(method = GET, produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public CollectionModel<UserDto> getUsers(
      @RequestParam(required = false, defaultValue = "1") int page,
      @RequestParam(required = false, defaultValue = "5") int size) {
    List<UserDto> users = userService.getPaginated(page, size);
    /*Link selLink = linkTo(UserController.class).withSelfRel();
    for (UserDto user : users) {
      Link link = linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel();
      user.add(link);
    }
    return CollectionModel.of(users, selLink);*/
    return hat.makeUserLinks(users, false);
  }

  @RequestMapping(value = "/{id}", method = GET, produces = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public UserDto getUser(@PathVariable int id) {
    UserDto user = userService.getById(id);
    /*Link selfLink = linkTo(methodOn(UserController.class).getUser(id)).withSelfRel();
    Link ordersLink =
        linkTo(methodOn(UserController.class).getUserOrders(id, null, null)).withRel("user orders");
    List<OrderDto> userOrders = user.getOrders();
    for (OrderDto userOrder : userOrders) {
      Link orderLink =
          linkTo(methodOn(UserController.class).getUserOrder(id, userOrder.getId())).withSelfRel();
      userOrder.add(orderLink);
    }
    user.add(selfLink);
    user.add(ordersLink);
    return user;*/
    return new ArrayList<>(hat.makeUserLinks(Arrays.asList(user), true).getContent()).get(0);
  }

  @RequestMapping(
      value = "/{userId}/orders",
      method = POST,
      produces = "application/json",
      consumes = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public OrderDto orderCertificate(@PathVariable int userId, @RequestBody OrderDto order) {
    OrderDto newOrder = orderService.orderCertificate(userId, order.getCertificate().getId());
    /*Link selfLink =
        linkTo(methodOn(UserController.class).orderCertificate(userId, null)).withSelfRel();
    newOrder.add(selfLink);
    return newOrder;*/
    return new ArrayList<>(
            hat.makeOrderLinks(Arrays.asList(newOrder), userId, true).getContent())
        .get(0);
  }

  @RequestMapping(value = "/{userId}/orders", method = GET, produces = "application/json")
  public CollectionModel<OrderDto> getUserOrders(
      @PathVariable int userId,
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(required = false, defaultValue = "5") Integer size) {
    List<OrderDto> orders =
        orderService.getPaginated(orderService.getUserOrders(userId), page, size);
    return hat.makeOrderLinks(orders, userId, false);
  }

  @RequestMapping(value = "/{userId}/orders/{orderId}", method = GET, produces = "application/json")
  public OrderDto getUserOrder(@PathVariable int userId, @PathVariable int orderId) {
    OrderDto order = orderService.getUserOrder(userId, orderId);
    /*Link selfLink = linkTo(methodOn(UserController.class).getUserOrder(userId, orderId)).withSelfRel();
    order.add(selfLink);
    return order;*/
    return new ArrayList<>(hat.makeOrderLinks(Arrays.asList(order), userId, false).getContent())
        .get(0);
  }

  @RequestMapping(value = "/most", method = GET)
  public TagDto getWidelyUsedTag() {
    TagDto mostWidelyUsedTag =  userService.getMostWidelyUsedTagOfRichestUser();
    return new ArrayList<>(
            hat.makeTagLinks(Set.of(mostWidelyUsedTag), true).getContent())
            .get(0);
  }
}
