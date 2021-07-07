package com.epam.esm.web.impl;

import com.epam.esm.converter.OrderConverter;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.Order;
import com.epam.esm.web.OrderRepository;
import com.epam.esm.web.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;

  @Autowired
  public OrderServiceImpl(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Override
  public List<OrderDto> getAll() {
    return orderRepository.findAll().stream()
        .map(OrderConverter::convertModelToDto)
        .collect(Collectors.toList());
  }

  @Override
  public OrderDto getById(int id) {
    Order order = orderRepository.findOne(id);
    if (order == null) {
      throw new ResourceNotFoundException("order with id = " + id + " does not exist", 40402);
    }
    return OrderConverter.convertModelToDto(orderRepository.findOne(id));
  }
}
