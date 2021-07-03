package com.epam.esm.web.impl;

import com.epam.esm.mapper.TagRowMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.web.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagRepositoryImpl implements TagRepository {
  private final NamedParameterJdbcOperations namedJdbcOperations;

  private final String GET_ALL_TAGS = "select * from tag";

  private final String GET_TAG_BY_ID = "select * from tag where id=?";

  private final String CREATE_TAG = "insert into tag(id, name) values (null, :name)";

  private final String DELETE_TAG = "delete from tag where id=?";

  private final String GET_TAG_BY_NAME = "select * from tag where name=?";

  private final String GET_TAGS_BY_CERTIFICATE_ID =
      "select tag.id as id, tag.name as name "
          + "from tag join certificateTags on tag.id=certificateTags.tag_id "
          + "where certificateTags.certificate_id=?";

  private final RowMapper<Tag> tagRowMapper;

  private final String IS_TAG_CONNECTED =
      "select certificate_id from certificateTags where tag_id=? limit 1";

  /**
   * Instantiates a new Tag repository.
   *
   * @param namedJdbcOperations the named jdbc operations
   */
  @Autowired
  public TagRepositoryImpl(NamedParameterJdbcOperations namedJdbcOperations) {
    this.namedJdbcOperations = namedJdbcOperations;
    tagRowMapper = new TagRowMapper();
  }

  /**
   * find all tags
   *
   * @return {@link List} of all tags
   */
  @Override
  public List<Tag> findAll() {
    return namedJdbcOperations.getJdbcOperations().query(GET_ALL_TAGS, tagRowMapper);
  }

  @Override
  public List<Tag> findByQuery(String sql, Class<Tag> elemType, Object... params) {
    return namedJdbcOperations.getJdbcOperations().queryForList(sql, elemType, params);
  }

  /**
   * find tag by tag name
   *
   * @param name tag name
   * @return {@link Tag}
   */
  @Override
  public Tag findByName(String name) {
    return namedJdbcOperations
        .getJdbcOperations()
        .queryForObject(GET_TAG_BY_NAME, tagRowMapper, name);
  }

  /**
   * find tag by id
   *
   * @param id id if the tag
   * @return {@link Tag}
   */
  @Override
  public Tag findOne(int id) {
    return namedJdbcOperations.getJdbcOperations().queryForObject(GET_TAG_BY_ID, tagRowMapper, id);
  }

  /**
   * create new record in db
   *
   * @param tag {@link Tag} with new values
   * @return last inserted id
   */
  @Override
  public int create(Tag tag) {
    GeneratedKeyHolder holder = new GeneratedKeyHolder();
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("name", tag.getName());
    int affectedRows = namedJdbcOperations.update(CREATE_TAG, params, holder);
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
   * check if this tag connected with one or more certificates
   *
   * @param id id of the tag
   * @return true if connected, false otherwise
   */
  public boolean isTagConnected(int id) {
    List<Integer> idList;
    idList =
        namedJdbcOperations
            .getJdbcOperations()
            .query(IS_TAG_CONNECTED, (rs, rowNum) -> rs.getInt("certificate_id"), id);
    return !idList.isEmpty();
  }

  /**
   * delete record of certain tag in db
   *
   * @param id id of the tag
   * @return 1 if everything was okay, 0 otherwise
   */
  @Override
  public int delete(int id) {
    return namedJdbcOperations.getJdbcOperations().update(DELETE_TAG, id);
  }

  /**
   * find all tag of certain {@link com.epam.esm.model.Certificate}
   *
   * @param certId id of the certificate
   * @return {@link List} of Tags connected to certificate
   */
  @Override
  public List<Tag> findTagsByCertificateId(int certId) {
    return namedJdbcOperations
        .getJdbcOperations()
        .query(GET_TAGS_BY_CERTIFICATE_ID, tagRowMapper, certId);
  }
}
