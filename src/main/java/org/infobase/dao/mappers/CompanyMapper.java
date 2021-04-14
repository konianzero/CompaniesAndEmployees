package org.infobase.dao.mappers;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.infobase.model.Company;

public final class CompanyMapper implements RowMapper<Company> {
    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
        Company company = new Company();
        company.setId(rs.getInt("id"));
        company.setName(rs.getString("name"));
        company.setTin(rs.getString("tin"));
        company.setAddress(rs.getString("address"));
        company.setPhoneNumber(rs.getString("phone_number"));
        return company;
    }
}
