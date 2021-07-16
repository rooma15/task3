package com.epam.esm.audit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "order_audit")
public class OrderAudit implements Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "order_id")
  private Integer orderId;

  @Column(name = "operation_time")
  private LocalDateTime operationTime;

  @Column(name = "operation_type")
  private String operationType;

  public OrderAudit() {}

  public OrderAudit(
      Integer id, Integer orderId, LocalDateTime operationTime, String operationType) {
    this.id = id;
    this.orderId = orderId;
    this.operationTime = operationTime;
    this.operationType = operationType;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getOrderId() {
    return orderId;
  }

  public void setOrderId(Integer certificateId) {
    this.orderId = certificateId;
  }

  public LocalDateTime getOperationTime() {
    return operationTime;
  }

  public void setOperationTime(LocalDateTime operationTime) {
    this.operationTime = operationTime;
  }

  public String getOperationType() {
    return operationType;
  }

  public void setOperationType(String operationType) {
    this.operationType = operationType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    OrderAudit that = (OrderAudit) o;
    return Objects.equals(id, that.id)
        && Objects.equals(orderId, that.orderId)
        && Objects.equals(operationTime, that.operationTime)
        && Objects.equals(operationType, that.operationType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, orderId, operationTime, operationType);
  }

  @Override
  public String toString() {
    return "OrderAudit{"
        + "id="
        + id
        + ", orderId="
        + orderId
        + ", operationTime="
        + operationTime
        + ", OperationType='"
        + operationType
        + '\''
        + '}';
  }
}
