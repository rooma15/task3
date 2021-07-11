package com.epam.esm.web.impl;

import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import com.epam.esm.web.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

  private final EntityManager entityManager;

  private final String FIND_ALL = "SELECT a FROM User a";

  @Autowired
  public UserRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<User> findAll() {
    entityManager.getTransaction().begin();
    List<User> users = entityManager.createQuery(FIND_ALL, User.class).getResultList();
    entityManager.getTransaction().commit();
    return users;
  }

  @Override
  public User findOne(int id) {
    entityManager.getTransaction().begin();
    User user = entityManager.find(User.class, id);
    entityManager.getTransaction().commit();
    return user;
  }

  @Override
  public Integer findMostUsedTag(String query, List<Object> parameters) {
    Query nativeQuery = entityManager.createNativeQuery(query);
    for (int i = 0; i < parameters.size(); i++) {
      nativeQuery.setParameter(i + 1, parameters.get(i));
    }
    List<Object[]> list =  nativeQuery.getResultList();
    return (Integer) list.get(0)[1];
  }

  @Override
  public List<User> getPaginated(Integer from, Integer count) {
    List<User> users =
            entityManager
                    .createQuery(FIND_ALL)
                    .setFirstResult(from)
                    .setMaxResults(count)
                    .getResultList();
    return users;
  }
}
