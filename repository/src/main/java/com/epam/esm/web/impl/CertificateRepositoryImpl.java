package com.epam.esm.web.impl;

import com.epam.esm.model.Certificate;
import com.epam.esm.web.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Repository
public class CertificateRepositoryImpl implements CertificateRepository {

  private final EntityManager entityManager;

  private final String MAKE_LINK =
      "insert into `gift-certificates`.certificateTags (id, certificate_id, tag_id) values (null, ?, ?)";

  private final String FIND_ALL = "SELECT a FROM Certificate a";

  @Autowired
  public CertificateRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Certificate update(Certificate certificate) {
    //entityManager.getTransaction().begin();
    Certificate existedCertificate = entityManager.find(Certificate.class, certificate.getId());
    if (existedCertificate == null) {
     // entityManager.getTransaction().rollback();
      throw new EntityNotFoundException();
    }
    if (certificate.getName() != null) {
      existedCertificate.setName(certificate.getName());
    }
    if (certificate.getDescription() != null) {
      existedCertificate.setDescription(certificate.getDescription());
    }
    if (certificate.getPrice() != null) {
      existedCertificate.setPrice(certificate.getPrice());
    }
    if (certificate.getDuration() != null) {
      existedCertificate.setDuration(certificate.getDuration());
    }
    existedCertificate.setLastUpdateDate(certificate.getLastUpdateDate());
    if (certificate.getTags() != null) {
      existedCertificate.setTags(certificate.getTags());
    }
   // entityManager.getTransaction().commit();
    return existedCertificate;
  }

  public void refresh(Certificate certificate) {
    entityManager.refresh(certificate);
  }

  @Override
  public void makeLink(int certificateId, int tagId) {
    entityManager.getTransaction().begin();
    entityManager
        .createNativeQuery(MAKE_LINK)
        .setParameter(1, certificateId)
        .setParameter(2, tagId)
        .executeUpdate();
    entityManager.getTransaction().commit();
  }

  @Override
  public List<Certificate> findAll() {
    entityManager.getTransaction().begin();
    List<Certificate> certificates =
        entityManager.createQuery(FIND_ALL, Certificate.class).getResultList();
    entityManager.getTransaction().commit();
    return certificates;
  }

  @Override
  public Certificate findOne(int id) {
    entityManager.getTransaction().begin();
    Certificate cert = entityManager.find(Certificate.class, id);
    entityManager.getTransaction().commit();
    return cert;
  }

  @Override
  public Certificate create(Certificate certificate) {
   // entityManager.getTransaction().begin();
    try {
      entityManager.persist(certificate);
     // entityManager.getTransaction().commit();
    } catch (Exception e) {
      //entityManager.getTransaction().rollback();
      throw e;
    }
    return certificate;
  }

  @Override
  public void delete(int id) {
    entityManager.getTransaction().begin();
    try {
      entityManager.remove(entityManager.getReference(Certificate.class, id));
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
      throw e;
    }
  }

  @Override
  public List<Certificate> getPaginated(Integer from, Integer count) {
    List<Certificate> certificates =
        entityManager
            .createQuery(FIND_ALL)
            .setFirstResult(from)
            .setMaxResults(count)
            .getResultList();
    return certificates;
  }
}
