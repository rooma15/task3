package com.epam.esm.web.impl;

import com.epam.esm.model.Tag;
import com.epam.esm.web.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/** The type Tag repository. */
@Repository
public class TagRepositoryImpl implements TagRepository {

  private final EntityManager entityManager;

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
   // entityManager.getTransaction().begin();
    List<Tag> tags = entityManager.createQuery(FIND_ALL, Tag.class).getResultList();
    //entityManager.getTransaction().commit();
    return tags;
  }

  @Override
  public Tag findOne(int id) {
    entityManager.getTransaction().begin();
    Tag tag = entityManager.find(Tag.class, id);
    entityManager.getTransaction().commit();
    return tag;
  }

  @Override
  public Tag create(Tag tag) {
    //entityManager.getTransaction().begin();
    try {
      entityManager.persist(tag);
      //entityManager.getTransaction().commit();
    } catch (Exception e) {
      //entityManager.getTransaction().rollback();
      throw e;
    }
    return tag;
  }

  @Override
  public void delete(int id) {
    entityManager.getTransaction().begin();
    try {
      entityManager.remove(entityManager.getReference(Tag.class, id));
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
      throw e;
    }
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
