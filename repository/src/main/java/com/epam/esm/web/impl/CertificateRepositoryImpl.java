package com.epam.esm.web.impl;

import com.epam.esm.model.Certificate;
import com.epam.esm.web.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CertificateRepositoryImpl implements CertificateRepository {

  @PersistenceContext private final EntityManager entityManager;

  private final String MAKE_LINK =
      "insert into `gift-certificates`.certificate_tags (id, certificate_id, tag_id) values (null, ?, ?)";

  private final String FIND_ALL = "SELECT a FROM Certificate a";

  @Autowired
  public CertificateRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Certificate update(Certificate certificate) {
    Certificate existedCertificate = entityManager.find(Certificate.class, certificate.getId());
    if (existedCertificate == null) {
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
    return existedCertificate;
  }

  public void refresh(Certificate certificate) {
    entityManager.refresh(certificate);
  }

  @Override
  @Transactional
  public void makeLink(int certificateId, int tagId) {
    entityManager
        .createNativeQuery(MAKE_LINK)
        .setParameter(1, certificateId)
        .setParameter(2, tagId)
        .executeUpdate();
  }

  @Override
  public List<Certificate> findAll() {
    return entityManager.createQuery(FIND_ALL, Certificate.class).getResultList();
  }

  @Override
  public Certificate findOne(int id) {
    return entityManager.find(Certificate.class, id);
  }

  @Override
  public Certificate create(Certificate certificate) {
    entityManager.persist(certificate);
    return certificate;
  }

  @Override
  public void delete(int id) {
    entityManager.remove(entityManager.getReference(Certificate.class, id));
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
