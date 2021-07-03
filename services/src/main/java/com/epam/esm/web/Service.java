package com.epam.esm.web;

import com.epam.esm.exception.ResourceNotFoundException;

import java.util.List;

public interface Service<T> {

  /**
   * Save entity in db
   *
   * @param EntityDto the entity dto
   * @return the t
   */
  T save(T EntityDto);

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
   * Delete entoty from db
   *
   * @param id the id
   * @return 1 if enerything was okay, 0 otherwise
   */
  int delete(int id);

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
}
