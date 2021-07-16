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
@Table(name = "user_audit")
public class UserAudit implements Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "user_id")
  private Integer userId;

  @Column(name = "operation_time")
  private LocalDateTime operationTime;

  @Column(name = "operation_type")
  private String operationType;

  public UserAudit() {}

  public UserAudit(Integer id, Integer userId, LocalDateTime operationTime, String operationType) {
    this.id = id;
    this.userId = userId;
    this.operationTime = operationTime;
    this.operationType = operationType;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer certificateId) {
    this.userId = certificateId;
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
    UserAudit userAudit = (UserAudit) o;
    return Objects.equals(id, userAudit.id)
        && Objects.equals(userId, userAudit.userId)
        && Objects.equals(operationTime, userAudit.operationTime)
        && Objects.equals(operationType, userAudit.operationType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userId, operationTime, operationType);
  }

  @Override
  public String toString() {
    return "UserAudit{"
        + "id="
        + id
        + ", userId="
        + userId
        + ", operationTime="
        + operationTime
        + ", operationType='"
        + operationType
        + '\''
        + '}';
  }
}
