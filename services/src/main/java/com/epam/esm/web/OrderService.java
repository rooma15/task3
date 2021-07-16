package com.epam.esm.web;

import com.epam.esm.dto.OrderDto;

import java.util.List;

public interface OrderService extends Service<OrderDto> {
  List<OrderDto> getAll();

  OrderDto getById(int id);

  List<OrderDto> getPaginated(Integer page, Integer size);

  OrderDto orderCertificate(int userId, int certificateId);

  List<OrderDto> getUserOrders(int userId);

  OrderDto getUserOrder(int userId, int orderId);
}
