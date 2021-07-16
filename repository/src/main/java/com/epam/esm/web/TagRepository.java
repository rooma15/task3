package com.epam.esm.web;

import com.epam.esm.model.Tag;

import java.util.List;

public interface TagRepository extends Repository<Tag> {
  Object doNativeGetQuery(String query, List<Object> parameters);

  Tag create(Tag Entity);

  void delete(int id);
}
