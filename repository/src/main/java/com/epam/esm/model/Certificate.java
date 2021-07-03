package com.epam.esm.model;

import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Certificate {
  private final Integer id;
  private final String name;
  private final String description;
  private final BigDecimal price;
  private final Integer duration;
  private final LocalDateTime createDate;
  private final LocalDateTime lastUpdateDate;

  public Certificate(
      Integer id,
      @NonNull String name,
      @NonNull String description,
      @NonNull BigDecimal price,
      @NonNull Integer duration,
      @NonNull LocalDateTime createDate,
      @NonNull LocalDateTime lastUpdateDate) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.duration = duration;
    this.createDate = createDate;
    this.lastUpdateDate = lastUpdateDate;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Certificate that = (Certificate) o;
    return Objects.equals(id, that.id)
        && Objects.equals(name, that.name)
        && Objects.equals(description, that.description)
        && Objects.equals(price, that.price)
        && Objects.equals(duration, that.duration)
        && Objects.equals(createDate, that.createDate)
        && Objects.equals(lastUpdateDate, that.lastUpdateDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, price, duration, createDate, lastUpdateDate);
  }

  @Override
  public String toString() {
    return "Certificate{"
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
        + ", createDate='"
        + createDate
        + '\''
        + ", lastUpdateDate='"
        + lastUpdateDate
        + '\''
        + '}';
  }
}
