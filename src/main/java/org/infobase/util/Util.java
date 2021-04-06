package org.infobase.util;

import org.infobase.model.Company;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Util {

    private Util() {
    }

    public static CompanyMapper getCompanyMapper() {
        return new CompanyMapper();
    }

    private static final class CompanyMapper implements RowMapper<Company> {
        public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
            Company company = new Company();
            company.setId(rs.getInt("id"));
            company.setName(rs.getString("name"));
            company.setTin(rs.getLong("tin"));
            company.setAddress(rs.getString("address"));
            company.setPhoneNumber(rs.getString("phone_number"));
            return company;
        }
    }
}
