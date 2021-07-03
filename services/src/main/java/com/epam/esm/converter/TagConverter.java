package com.epam.esm.util;

import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Tag;

public class TagConverter {
  public static TagDto convertModelToDto(Tag tag) {
    return new TagDto(tag.getId(), tag.getName());
  }

  public static Tag convertDtoToModel(TagDto tag) {
    return new Tag(tag.getId(), tag.getName());
  }
}
