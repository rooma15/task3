package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class OrderDto extends RepresentationModel<OrderDto> {
  private Integer id;

  @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
  private LocalDateTime purchaseTime;

  private BigDecimal cost;
  private CertificateDto certificate;

  public OrderDto(
      Integer id, LocalDateTime purchaseTime, BigDecimal cost, CertificateDto certificate) {
    this.id = id;
    this.purchaseTime = purchaseTime;
    this.cost = cost;
    this.certificate = certificate;
  }

  public OrderDto(LocalDateTime purchaseTime, BigDecimal cost, CertificateDto certificate) {
    this.purchaseTime = purchaseTime;
    this.cost = cost;
    this.certificate = certificate;
  }

  public OrderDto() {}

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

  public CertificateDto getCertificate() {
    return certificate;
  }

  public void setCertificate(CertificateDto certificate) {
    this.certificate = certificate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    OrderDto orderDto = (OrderDto) o;
    return Objects.equals(id, orderDto.id)
        && Objects.equals(purchaseTime, orderDto.purchaseTime)
        && Objects.equals(cost, orderDto.cost)
        && Objects.equals(certificate, orderDto.certificate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, purchaseTime, cost, certificate);
  }

  @Override
  public String toString() {
    return "OrderDto{"
        + "id="
        + id
        + ", purchaseTime="
        + purchaseTime
        + ", cost="
        + cost
        + ", certificate="
        + certificate
        + '}';
  }
}
