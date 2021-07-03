package com.epam.esm.web.impl;

import com.epam.esm.mapper.CertificateRowMapper;
import com.epam.esm.model.Certificate;
import com.epam.esm.web.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class CertificateRepositoryImpl implements CertificateRepository {

  private final String GET_ALL_CERTIFICATES = "SELECT * FROM certificate";
  private final String GET_CERTIFICATE_BY_ID = "SELECT * FROM certificate where id=?";
  private final String CREATE_CERTIFICATE =
      "INSERT INTO certificate"
          + "(id, name, description, price, duration, create_date, last_update_date)"
          + "VALUES(null, :name, :description, :price, :duration, :create_date, :last_update_date)";

  private final String DELETE_CERTIFICATE = "delete from certificate where id=?";

  private final String GET_CERTIFICATES_BY_TAG_ID =
      "select certificate.id as id, name, description, price, "
          + "duration, create_date, last_update_date "
          + "from certificateTags join certificate on certificateTags.certificate_id=certificate.id"
          + " where tag_id=?";

  private final String FULL_UPDATE_CERTIFICATE =
      "update certificate set name=:name, description=:description, "
          + "price=:price, duration=:duration, create_date=:create_date, last_update_date=:last_update_date "
          + "where id=:id";

  private final String ADD_LINK =
      "insert into certificateTags(id, certificate_id, tag_id)" + "VALUES(null, ?, ?)";

  private final String DELETE_LINK =
      "delete from certificateTags where certificate_id=? and tag_id=?";

  private final String DELETE_LINK_BY_CERTIFICATE_ID =
      "delete from certificateTags where certificate_id=?";

  private NamedParameterJdbcOperations namedJdbcOperations;

  private final RowMapper<Certificate> certificateRowMapper;

  /**
   * Instantiates a new Certificate repository.
   *
   * @param jdbcOperations the jdbc operations
   */
  @Autowired
  public CertificateRepositoryImpl(NamedParameterJdbcOperations jdbcOperations) {
    this.namedJdbcOperations = jdbcOperations;
    certificateRowMapper = new CertificateRowMapper();
  }

  /**
   * make a connection between certificate and tag
   *
   * @param certId id of the {@link Certificate}
   * @param tagId id of the {@link com.epam.esm.model.Tag}
   * @return 1 if everythinf was okay, 0 otherwise
   */
  @Override
  @Transactional
  public int makeLink(int certId, int tagId) {
    return namedJdbcOperations.getJdbcOperations().update(ADD_LINK, certId, tagId);
  }

  /**
   * delete a connection between certificate and tag
   *
   * @param certId id of the {@link Certificate}
   * @param tagId id of the {@link com.epam.esm.model.Tag}
   * @return 1 if everythinf was okay, 0 otherwise
   */
  @Override
  @Transactional
  public int deleteLink(int certId, int tagId) {
    return namedJdbcOperations.getJdbcOperations().update(DELETE_LINK, certId, tagId);
  }
  /**
   * delete a connection between certificate and all tags of this certificate
   *
   * @param certId id of the {@link Certificate}
   * @return 1 if everythinf was okay, 0 otherwise
   */
  @Override
  @Transactional
  public int deleteLink(int certId) {
    return namedJdbcOperations.getJdbcOperations().update(DELETE_LINK_BY_CERTIFICATE_ID, certId);
  }

  /**
   * finds all {@link Certificate} in db
   *
   * @return {@link List} if the certificates
   */
  @Override
  public List<Certificate> findAll() {
    return namedJdbcOperations.query(GET_ALL_CERTIFICATES, certificateRowMapper);
  }

  /**
   * finds {@link Certificate} by id
   *
   * @param id id of the certificate
   * @return {@link Certificate} with id
   */
  @Override
  public Certificate findOne(int id) {
    return namedJdbcOperations
        .getJdbcOperations()
        .queryForObject(GET_CERTIFICATE_BY_ID, certificateRowMapper, id);
  }

  /**
   * delete certificate with id
   *
   * @param id id of the certificate
   * @return 1 if everything was okay, 0 otherwise
   */
  @Override
  public int delete(int id) {
    return namedJdbcOperations.getJdbcOperations().update(DELETE_CERTIFICATE, id);
  }

  @Override
  public List<Certificate> findByQuery(String sql, Class<Certificate> elemType, Object... params) {
    return namedJdbcOperations.getJdbcOperations().queryForList(sql, elemType, params);
  }

  /**
   * create {@link Certificate}
   *
   * @param certificate {@link Certificate} with fileds to be saved in db
   * @return id of certificated in db if everything is okay, 0 otherwise
   */
  @Override
  public int create(Certificate certificate) {
    GeneratedKeyHolder holder = new GeneratedKeyHolder();
    MapSqlParameterSource params = new MapSqlParameterSource();
    params
        .addValue("name", certificate.getName())
        .addValue("description", certificate.getDescription())
        .addValue("price", certificate.getPrice())
        .addValue("duration", certificate.getDuration())
        .addValue("create_date", certificate.getCreateDate())
        .addValue("last_update_date", certificate.getLastUpdateDate());
    int affectedRows = namedJdbcOperations.update(CREATE_CERTIFICATE, params, holder);
    if (affectedRows > 0) {
      if (holder.getKey() == null) {
        return 0;
      } else {
        return holder.getKey().intValue();
      }
    } else {
      return 0;
    }
  }

  /**
   * update certificate in db
   *
   * @param sql sql query for update
   * @param paramsMap {@link Map} with parameters (COLUMN NAME AS KEY, FIELD AS VALUE)
   * @param id id of the certificate
   * @return number of affected rows
   */
  @Override
  public int update(String sql, Map<String, Object> paramsMap, int id) {
    MapSqlParameterSource parameterSource = new MapSqlParameterSource(paramsMap);
    return namedJdbcOperations.update(sql, parameterSource);
  }

  /**
   * find certificates with certain tag
   *
   * @param tagId id of tag
   * @return {@link List} of the certificates with this tag
   */
  @Override
  public List<Certificate> findCertificatesByTagId(int tagId) {
    return namedJdbcOperations
        .getJdbcOperations()
        .query(GET_CERTIFICATES_BY_TAG_ID, certificateRowMapper, tagId);
  }

  /**
   * update certificate in db
   *
   * @param certificate {@link Certificate} with new values of certificate
   * @return number of affected rows
   */
  @Override
  public int update(Certificate certificate) {
    MapSqlParameterSource parameterSource = new MapSqlParameterSource();
    parameterSource
        .addValue("name", certificate.getName())
        .addValue("id", certificate.getId())
        .addValue("description", certificate.getDescription())
        .addValue("price", certificate.getPrice())
        .addValue("duration", certificate.getDuration())
        .addValue("create_date", certificate.getCreateDate())
        .addValue("last_update_date", certificate.getLastUpdateDate());
    return namedJdbcOperations.update(FULL_UPDATE_CERTIFICATE, parameterSource);
  }

  @Override
  public List<String> getColumnNames() {
    List<String> columnNames =
        new ArrayList<>(
            namedJdbcOperations
                .getJdbcOperations()
                .queryForMap("Select name, description, price, duration from certificate limit 1")
                .keySet());
    columnNames.add("tags");
    return columnNames;
  }
}
