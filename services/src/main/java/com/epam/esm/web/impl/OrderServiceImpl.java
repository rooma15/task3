package com.epam.esm.web.impl;

import com.epam.esm.converter.OrderConverter;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.DuplicateResourceException;
import com.epam.esm.exception.PaginationException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.localization.LocaleTranslator;
import com.epam.esm.model.Order;
import com.epam.esm.validator.PageValidator;
import com.epam.esm.web.CertificateService;
import com.epam.esm.web.OrderRepository;
import com.epam.esm.web.OrderService;
import com.epam.esm.web.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final PageValidator pageValidator;
  private final UserService userService;
  private final CertificateService certificateService;

  @Autowired
  public OrderServiceImpl(
      OrderRepository orderRepository,
      PageValidator pageValidator,
      UserService userService,
      CertificateService certificateService) {
    this.orderRepository = orderRepository;
    this.pageValidator = pageValidator;
    this.userService = userService;
    this.certificateService = certificateService;
  }

  @Override
  @Transactional
  public List<OrderDto> getAll() {
    return orderRepository.findAll().stream()
        .map(OrderConverter::convertModelToDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public OrderDto getById(int id) {
    Order order = orderRepository.findOne(id);
    if (order == null) {
      String formattedException =
          String.format(LocaleTranslator.translate("order.doesNotExist"), id);
      throw new ResourceNotFoundException(formattedException, 40403);
    }
    return OrderConverter.convertModelToDto(orderRepository.findOne(id));
  }

  @Override
  @Transactional
  public List<OrderDto> getPaginated(Integer page, Integer size) {
    pageValidator.validate(page, size);
    int from = (page - 1) * size;
    List<OrderDto> orders =
        orderRepository.getPaginated(from, size).stream()
            .map(OrderConverter::convertModelToDto)
            .collect(Collectors.toList());
    if (orders.isEmpty()) {
      throw new PaginationException(LocaleTranslator.translate("page.doesNotExist"), 404);
    }
    return orders;
  }

  @Override
  @Transactional
  public OrderDto orderCertificate(int userId, int certificateId) {
    UserDto user = userService.getById(userId);
    CertificateDto certificate = certificateService.getById(certificateId);
    OrderDto order = new OrderDto(LocalDateTime.now(), certificate.getPrice(), certificate);
    List<OrderDto> userOrders = user.getOrders();
    for (OrderDto userOrder : userOrders) {
      if (userOrder.getCertificate().getId() == certificateId) {
        String formattedException =
            String.format(
                LocaleTranslator.translate("order.duplicatedOrder"), userId, certificateId);
        throw new DuplicateResourceException(formattedException, 40903);
      }
    }
    return OrderConverter.convertModelToDto(
        orderRepository.order(userId, OrderConverter.convertDtoToModel(order)));
  }

  @Override
  @Transactional
  public List<OrderDto> getUserOrders(int userId) {
    UserDto user = userService.getById(userId);
    return user.getOrders();
  }

  @Override
  @Transactional
  public OrderDto getUserOrder(int userId, int orderId) {
    UserDto user = userService.getById(userId);
    Optional<OrderDto> optionalOrder =
        user.getOrders().stream().filter(order -> order.getId() == orderId).findFirst();
    if (optionalOrder.isPresent()) {
      return optionalOrder.get();
    } else {
      String formattedException =
          String.format(LocaleTranslator.translate("order.userNotOrdered"), userId, orderId);
      throw new ResourceNotFoundException(formattedException, 40403);
    }
  }
}
