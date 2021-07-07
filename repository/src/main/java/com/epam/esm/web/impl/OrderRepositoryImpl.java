package com.epam.esm.web.impl;

import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import com.epam.esm.web.OrderRepository;
import org.hibernate.internal.build.AllowPrintStacktrace;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final EntityManager entityManager;

    private final String FIND_ALL = "SELECT a FROM Order a";

    @AllowPrintStacktrace
    public OrderRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Order> findAll() {
        entityManager.getTransaction().begin();
        List<Order> orders = entityManager.createQuery(FIND_ALL, Order.class).getResultList();
        entityManager.getTransaction().commit();
        return orders;
    }

    @Override
    public Order findOne(int id) {
        entityManager.getTransaction().begin();
        Order order = entityManager.find(Order.class, id);
        entityManager.getTransaction().commit();
        return order;
    }

    @Override
    public Order create(Order order) {
        entityManager.getTransaction().begin();
        try{
            entityManager.persist(order);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            throw e;
        }
        return order;
    }
}
