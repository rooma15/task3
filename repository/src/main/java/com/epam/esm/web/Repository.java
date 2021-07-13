package com.epam.esm.web;

import java.util.List;

public interface Repository<T> {

  List<T> findAll();

  T findOne(int id);

  List<T> getPaginated(Integer page, Integer size);
}
