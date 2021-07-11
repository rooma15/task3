package com.epam.esm.model;

import com.epam.esm.audit.auditor.AuditHelper;
import com.epam.esm.audit.model.OrderAudit;
import com.epam.esm.audit.model.TagAudit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tag")
public class Tag {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  public Tag(Integer id, String name) {
    this.id = id;
    this.name = name;
  }


  @PostPersist
  public void onPostPersist(){
    audit("INSERT");
  }

  @PostUpdate
  public void onPostUpdate(){
    audit("UPDATE");
  }

  @PostRemove
  public void onPostRemove(){
    audit("REMOVE");
  }

  private void audit(String operationType){
    AuditHelper auditHelper = new AuditHelper();
    TagAudit tagAudit = new TagAudit();
    tagAudit.setTagId(id);
    tagAudit.setOperationTime(LocalDateTime.now());
    tagAudit.setOperationType(operationType);
    auditHelper.save(tagAudit);
  }

  public Tag() {}

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
    Tag tag = (Tag) o;
    return Objects.equals(id, tag.id) && Objects.equals(name, tag.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String toString() {
    return "Tag{" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}
