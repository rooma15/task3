package com.epam.esm.web;

import com.epam.esm.model.Certificate;

import java.util.List;
import java.util.Map;

public interface CertificateRepository extends Repository<Certificate> {
  int update(String sql, Map<String, Object> parameterSource, int id);

  List<Certificate> findCertificatesByTagId(int tagId);

  int update(Certificate certificate);

  List<String> getColumnNames();

  int makeLink(int certId, int tagId);

  int deleteLink(int certId, int tagId);

  int deleteLink(int certId);
}
