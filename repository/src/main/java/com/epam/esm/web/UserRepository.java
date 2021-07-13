package com.epam.esm.web;

import com.epam.esm.model.User;

import java.util.List;

public interface UserRepository extends Repository<User> {
  List<User> findAll();

  User findOne(int id);

  Integer findMostUsedTag(String query, List<Object> params);
}
