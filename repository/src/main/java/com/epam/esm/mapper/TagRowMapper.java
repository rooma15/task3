package com.epam.esm.mapper;

import com.epam.esm.model.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagRowMapper implements RowMapper<Tag> {
  /**
   * Row mapper for Tag
   *
   * @param rs {@link ResultSet} of the request to db
   * @param rowNum number of rows in the resultSet
   * @return {@link Tag} made from request result
   * @throws SQLException
   */
  @Override
  public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new Tag(rs.getInt("id"), rs.getString("name"));
  }
}
