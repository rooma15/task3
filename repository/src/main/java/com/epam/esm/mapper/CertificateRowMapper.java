package com.epam.esm.mapper;

import com.epam.esm.model.Certificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CertificateRowMapper implements RowMapper<Certificate> {
    /**
     * Row mapper for certificate
     * @param rs {@link ResultSet} of the request to db
     * @param rowNum number of rows in the resultSet
     * @return {@link Certificate} made from request result
     * @throws SQLException
     */
    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Certificate(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getBigDecimal("price"),
                rs.getInt("duration"),
                rs.getTimestamp("create_date").toLocalDateTime(),
                rs.getTimestamp("last_update_date").toLocalDateTime()
        );
    }
}
