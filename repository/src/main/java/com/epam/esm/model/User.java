package com.epam.esm.model;

import com.epam.esm.audit.auditor.AuditHelper;
import com.epam.esm.audit.model.UserAudit;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  private List<Order> orders = new ArrayList<>();

  public User(Integer id, String firstName, String lastName, List<Order> orders) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.orders = orders;
  }

  @PostPersist
  public void onPostPersist() {
    audit("INSERT");
  }

  @PostUpdate
  public void onPostUpdate() {
    audit("UPDATE");
  }

  @PostRemove
  public void onPostRemove() {
    audit("REMOVE");
  }

  private void audit(String operationType) {
    AuditHelper auditHelper = new AuditHelper();
    UserAudit user = new UserAudit();
    user.setUserId(id);
    user.setOperationTime(LocalDateTime.now());
    user.setOperationType(operationType);
    auditHelper.save(user);
  }

  public User() {}

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(id, user.id)
        && Objects.equals(firstName, user.firstName)
        && Objects.equals(lastName, user.lastName)
        && Objects.equals(orders, user.orders);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, orders);
  }

  @Override
  public String toString() {
    return "User{"
        + "id="
        + id
        + ", firstName='"
        + firstName
        + '\''
        + ", last_name='"
        + lastName
        + '\''
        + ", orders="
        + orders
        + '}';
  }
}
