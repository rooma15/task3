package com.epam.esm.web;


import com.epam.esm.exception.PaginationException;
import com.epam.esm.exception.ResourceNotFoundException;

import java.util.List;

public interface Service<T> {


  /**
   * Gets all entities from db
   *
   * @return {@link List} of entities
   */
  List<T> getAll();

  /**
   * Gets entity by id.
   *
   * @param id the id
   * @return entity
   */
  T getById(int id);



  /**
   * checks if resource exisits
   *
   * @param id the id of the resource
   * @return true if resource exists, false otherwise
   */
  default boolean isResourceExist(int id) {
    try {
      getById(id);
    } catch (ResourceNotFoundException e) {
      return false;
    }
    return true;
  }


  default List<T> getPaginated(List<T> entities, Integer page, Integer size) {
    if(page < 1){
      throw new PaginationException("page is invalid", 403);
    }
    if(size < 0){
      throw new PaginationException("size must be more or equals 0 ", 403);
    }
    int from  = (page - 1) * size;
    int to = Math.min((page - 1) * size + size, entities.size());
    if(from > to){
      throw new PaginationException("combination page-size is not valid", 403);
    }
    return entities.subList(from, to);
  }
}
