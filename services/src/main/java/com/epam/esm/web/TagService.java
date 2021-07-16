package com.epam.esm.web;

import com.epam.esm.dto.TagDto;

import java.util.List;

public interface TagService extends Service<TagDto> {
  List<TagDto> getPaginated(Integer page, Integer size);

  /**
   * Save entity in db
   *
   * @param EntityDto the entity dto
   * @return the t
   */
  TagDto save(TagDto EntityDto);

  /**
   * Delete entoty from db
   *
   * @param id the id
   * @return 1 if enerything was okay, 0 otherwise
   */
  int delete(int id);
}
