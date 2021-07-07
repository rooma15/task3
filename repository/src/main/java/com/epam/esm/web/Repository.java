package com.epam.esm.web;

import java.util.List;

public interface Repository<T> {
  List<T> findAll();

  T findOne(int id);


  T create(T Entity);

  void delete(int id);
}
