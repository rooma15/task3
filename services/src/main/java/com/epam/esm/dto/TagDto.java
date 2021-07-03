package com.epam.esm.dto;

import java.util.Objects;

public class TagDto {
  private Integer id;
  private String name;

  public TagDto(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public TagDto(String name) {
    this.name = name;
  }

  public TagDto() {}

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TagDto tagDto = (TagDto) o;
    return Objects.equals(id, tagDto.id) && Objects.equals(name, tagDto.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String toString() {
    return "TagDto{" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}
