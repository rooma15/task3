package com.epam.esm.web;

import com.epam.esm.model.Certificate;

public interface CertificateRepository extends Repository<Certificate> {

  void refresh(Certificate certificate);

  void makeLink(int certificateId, int tagId);

  Certificate update(Certificate certificate);

  Certificate create(Certificate Entity);

  void delete(int id);
}
