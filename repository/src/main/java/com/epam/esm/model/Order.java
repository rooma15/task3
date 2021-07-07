package com.epam.esm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "purchase_time")
  private LocalDateTime purchaseTime;

  private BigDecimal cost;
  @OneToOne
  @JoinColumn(name = "certificate_id")
  private Certificate certificate;

  public Order(Integer id, LocalDateTime purchaseTime, BigDecimal cost, Certificate certificate) {
    this.id = id;
    this.purchaseTime = purchaseTime;
    this.cost = cost;
    this.certificate = certificate;
  }

  public Order() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public LocalDateTime getPurchaseTime() {
    return purchaseTime;
  }

  public void setPurchaseTime(LocalDateTime purchaseTime) {
    this.purchaseTime = purchaseTime;
  }

  public BigDecimal getCost() {
    return cost;
  }

  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }

  public Certificate getCertificate() {
    return certificate;
  }

  public void setCertificate(Certificate certificate) {
    this.certificate = certificate;
  }

  @Override
  public boolean equals(Object o) {
    if(this == o) return true;
    if(o == null || getClass() != o.getClass()) return false;
    Order order = (Order) o;
    return Objects.equals(id, order.id) &&
            Objects.equals(purchaseTime, order.purchaseTime) &&
            Objects.equals(cost, order.cost) &&
            Objects.equals(certificate, order.certificate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, purchaseTime, cost, certificate);
  }

  @Override
  public String toString() {
    return "Order{" +
            "id=" + id +
            ", purchaseTime=" + purchaseTime +
            ", cost=" + cost +
            ", certificate=" + certificate +
            '}';
  }
}
