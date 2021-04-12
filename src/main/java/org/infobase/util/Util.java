package org.infobase.util;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.infobase.model.Company;
import org.infobase.model.Employee;

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
            company.setTin(rs.getString("tin"));
            company.setAddress(rs.getString("address"));
            company.setPhoneNumber(rs.getString("phone_number"));
            return company;
        }
    }

    public static EmployeeMapper getEmployeeMapper() {
        return new EmployeeMapper();
    }

    private static final class EmployeeMapper implements RowMapper<Employee> {
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee();
            employee.setId(rs.getInt("emp_id"));
            employee.setName(rs.getString("emp_name"));
            employee.setBirthDate(rs.getDate("birth_date").toLocalDate());
            employee.setEmail(rs.getString("email"));
            employee.setCompany(
                    new Company(
                            rs.getInt("comp_id"),
                            rs.getString("comp_name"),
                            rs.getString("tin"),
                            rs.getString("address"),
                            rs.getString("phone_number")
                    )
            );
            return employee;
        }
    }
}
