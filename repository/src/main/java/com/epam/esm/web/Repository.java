package com.epam.esm.web;

import java.util.List;

public interface Repository<T> {
  List<T> findAll();

  T findOne(int id);

  List<T> findByQuery(String sql, Class<T> elemType, Object... params);

  int create(T Entity);

  int delete(int id);
}
