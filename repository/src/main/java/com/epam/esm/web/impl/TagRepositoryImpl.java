package com.epam.esm.web.impl;

import com.epam.esm.model.Tag;
import com.epam.esm.web.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/** The type Tag repository. */
@Repository
public class TagRepositoryImpl implements TagRepository {

  @PersistenceContext private final EntityManager entityManager;

  private final String FIND_ALL = "SELECT a FROM Tag a";

  /**
   * Instantiates a new Tag repository.
   *
   * @param entityManager the entity manager
   */
  @Autowired
  public TagRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<Tag> findAll() {
    return entityManager.createQuery(FIND_ALL, Tag.class).getResultList();
  }

  @Override
  public Tag findOne(int id) {
    return entityManager.find(Tag.class, id);
  }

  @Override
  public Tag create(Tag tag) {
    entityManager.persist(tag);
    return tag;
  }

  @Override
  public void delete(int id) {
    entityManager.remove(entityManager.getReference(Tag.class, id));
  }

  @Override
  public Object doNativeGetQuery(String query, List<Object> parameters) {
    Query nativeQuery = entityManager.createNativeQuery(query);
    for (int i = 0; i < parameters.size(); i++) {
      nativeQuery.setParameter(i + 1, parameters.get(i));
    }
    return nativeQuery.getSingleResult();
  }

  @Override
  public List<Tag> getPaginated(Integer from, Integer count) {
    List<Tag> tags =
        entityManager
            .createQuery(FIND_ALL)
            .setFirstResult(from)
            .setMaxResults(count)
            .getResultList();
    return tags;
  }
}
