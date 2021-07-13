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
@Table(name = "tag_audit")
public class TagAudit implements Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "tag_id")
  private Integer tagId;

  @Column(name = "operation_time")
  private LocalDateTime operationTime;

  @Column(name = "operation_type")
  private String operationType;

  public TagAudit() {}

  public TagAudit(Integer id, Integer tagId, LocalDateTime operationTime, String operationType) {
    this.id = id;
    this.tagId = tagId;
    this.operationTime = operationTime;
    this.operationType = operationType;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getTagId() {
    return tagId;
  }

  public void setTagId(Integer certificateId) {
    this.tagId = certificateId;
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
    TagAudit tagAudit = (TagAudit) o;
    return Objects.equals(id, tagAudit.id)
        && Objects.equals(tagId, tagAudit.tagId)
        && Objects.equals(operationTime, tagAudit.operationTime)
        && Objects.equals(operationType, tagAudit.operationType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, tagId, operationTime, operationType);
  }

  @Override
  public String toString() {
    return "TagAudit{"
        + "id="
        + id
        + ", tagId="
        + tagId
        + ", operationTime="
        + operationTime
        + ", operationType='"
        + operationType
        + '\''
        + '}';
  }
}
