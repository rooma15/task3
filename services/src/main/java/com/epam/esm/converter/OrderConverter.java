package com.epam.esm.converter;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.model.Order;

public class OrderConverter {
  public static Order convertDtoToModel(OrderDto orderDto) {
    return new Order(
        orderDto.getId(),
        orderDto.getPurchaseTime(),
        orderDto.getCost(),
        CertificateConverter.convertDtoToModel(orderDto.getCertificate()));
  }

  public static OrderDto convertModelToDto(Order order) {
    return new OrderDto(
        order.getId(),
        order.getPurchaseTime(),
        order.getCost(),
        CertificateConverter.convertModelToDto(order.getCertificate()));
  }
}
