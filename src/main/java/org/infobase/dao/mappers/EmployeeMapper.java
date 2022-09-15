package org.infobase.dao.mappers;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.infobase.model.Company;
import org.infobase.model.Employee;
import org.springframework.stereotype.Component;

// TODO - Make a Bean
/**
 * Класс для сопоставления строк результирующего набора с сущностью сотрудника
 */
@Component
public final class EmployeeMapper implements RowMapper<Employee> {
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
