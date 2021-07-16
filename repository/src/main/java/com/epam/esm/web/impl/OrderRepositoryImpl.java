package com.epam.esm.web.impl;

import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import com.epam.esm.web.OrderRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

  @PersistenceContext private final EntityManager entityManager;

  private final String FIND_ALL = "SELECT a FROM Order a";

  public OrderRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<Order> findAll() {
    return entityManager.createQuery(FIND_ALL, Order.class).getResultList();
  }

  @Override
  public Order findOne(int id) {
    return entityManager.find(Order.class, id);
  }

  @Override
  public Order create(Order order) {
    entityManager.persist(order);
    return order;
  }

  @Override
  public List<Order> getPaginated(Integer from, Integer count) {
    List<Order> orders =
        entityManager
            .createQuery(FIND_ALL)
            .setFirstResult(from)
            .setMaxResults(count)
            .getResultList();
    return orders;
  }

  @Override
  public Order order(Integer userId, Order order) {
    User existedUser = entityManager.find(User.class, userId);
    List<Order> orders = existedUser.getOrders();
    orders.add(order);
    existedUser.setOrders(orders);
    entityManager.refresh(existedUser);
    return order;
  }
}
