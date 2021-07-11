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
@Table(name = "certificateAudit")
public class CertificateAudit implements Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "certificate_id")
  private Integer certificateId;

  @Column(name = "operation_time")
  private LocalDateTime operationTime;

  @Column(name = "operation_type")
  private String OperationType;

  public CertificateAudit() {}

  public CertificateAudit(
      Integer id, Integer certificateId, LocalDateTime operationTime, String operationType) {
    this.id = id;
    this.certificateId = certificateId;
    this.operationTime = operationTime;
    OperationType = operationType;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getCertificateId() {
    return certificateId;
  }

  public void setCertificateId(Integer certificateId) {
    this.certificateId = certificateId;
  }

  public LocalDateTime getOperationTime() {
    return operationTime;
  }

  public void setOperationTime(LocalDateTime operationTime) {
    this.operationTime = operationTime;
  }

  public String getOperationType() {
    return OperationType;
  }

  public void setOperationType(String operationType) {
    OperationType = operationType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CertificateAudit that = (CertificateAudit) o;
    return Objects.equals(id, that.id)
        && Objects.equals(certificateId, that.certificateId)
        && Objects.equals(operationTime, that.operationTime)
        && Objects.equals(OperationType, that.OperationType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, certificateId, operationTime, OperationType);
  }

  @Override
  public String toString() {
    return "CertificateAudit{"
        + "id="
        + id
        + ", certificateId="
        + certificateId
        + ", operationTime="
        + operationTime
        + ", OperationType='"
        + OperationType
        + '\''
        + '}';
  }
}
