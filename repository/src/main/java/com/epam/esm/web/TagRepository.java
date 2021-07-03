package com.epam.esm.web;

import com.epam.esm.model.Tag;

import java.util.List;

public interface TagRepository extends Repository<Tag> {
  Tag findByName(String name);

  List<Tag> findTagsByCertificateId(int certId);

  boolean isTagConnected(int id);
}
