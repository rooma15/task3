package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class CertificateDto {
  private Integer id;
  private String name;
  private String description;
  private BigDecimal price;
  private Integer duration;

  @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
  private LocalDateTime createDate;

  @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
  private LocalDateTime lastUpdateDate;

  private List<TagDto> tags;

  public CertificateDto(
      Integer id,
      String name,
      String description,
      BigDecimal price,
      Integer duration,
      LocalDateTime createDate,
      LocalDateTime lastUpdateDate,
      List<TagDto> tags) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.duration = duration;
    this.createDate = createDate;
    this.lastUpdateDate = lastUpdateDate;
    this.tags = tags;
  }

  public CertificateDto() {}

  public CertificateDto(
      String name, String description, BigDecimal price, Integer duration, List<TagDto> tags) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.duration = duration;
    this.tags = tags;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public Integer getDuration() {
    return duration;
  }

  public LocalDateTime getCreateDate() {
    return createDate;
  }

  public LocalDateTime getLastUpdateDate() {
    return lastUpdateDate;
  }

  public List<TagDto> getTags() {
    return tags;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  public void setCreateDate(LocalDateTime createDate) {
    this.createDate = createDate;
  }

  public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
    this.lastUpdateDate = lastUpdateDate;
  }

  public void setTags(List<TagDto> tags) {
    this.tags = tags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CertificateDto that = (CertificateDto) o;
    return Objects.equals(id, that.id)
        && Objects.equals(name, that.name)
        && Objects.equals(description, that.description)
        && Objects.equals(price, that.price)
        && Objects.equals(duration, that.duration)
        && Objects.equals(createDate, that.createDate)
        && Objects.equals(lastUpdateDate, that.lastUpdateDate)
        && Objects.equals(tags, that.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, price, duration, createDate, lastUpdateDate, tags);
  }

  @Override
  public String toString() {
    return "CertificateDto{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", description='"
        + description
        + '\''
        + ", price="
        + price
        + ", duration="
        + duration
        + ", createDate="
        + createDate
        + ", lastUpdateDate="
        + lastUpdateDate
        + ", tags="
        + tags
        + '}';
  }
}
