package com.epam.esm.web;

import com.epam.esm.model.Order;
import com.epam.esm.model.User;

import java.util.List;

public interface UserRepository {
  List<User> findAll();

  User findOne(int id);

  User order(int userId, Order order);
}
