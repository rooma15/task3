package com.epam.esm.web;

import com.epam.esm.model.Order;

import java.util.List;

public interface OrderRepository extends Repository<Order> {
  List<Order> findAll();

  Order findOne(int id);

  Order create(Order order);

  Order order(Integer userId, Order order);
}
