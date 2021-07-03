package com.epam.esm.web;

import com.epam.esm.dto.TagDto;

import java.util.List;

public interface TagService extends Service<TagDto> {
  TagDto getByTagName(String name);

  List<TagDto> getTagsByCertificateId(int certId);

  boolean isResourceExist(String name);
}
