package com.epam.esm.validator;

public interface Validator<T> {

  /**
   * Validate Entity
   *
   * @param entity the entity
   * @return true if entuty is valid, false otherwise
   */
  abstract boolean validate(T entity);
}
