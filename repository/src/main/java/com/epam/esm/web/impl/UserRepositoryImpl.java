package com.epam.esm.web.impl;

import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import com.epam.esm.web.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
  public User order(int userId, Order order) {
    entityManager.getTransaction().begin();
    User existedUser = entityManager.find(User.class, userId);
    List<Order> orders = existedUser.getOrders();
    orders.add(order);
    try{
      existedUser.setOrders(orders);
      entityManager.getTransaction().commit();
      entityManager.refresh(existedUser);
    }catch (Exception e){
      entityManager.getTransaction().rollback();
      throw e;
    }
    return existedUser;
  }
}
