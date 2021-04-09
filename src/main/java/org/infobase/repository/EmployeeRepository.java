package org.infobase.repository;

import org.infobase.model.Employee;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static org.infobase.util.Util.getEmployeeMapper;

@Repository
public class EmployeeRepository {

    private static final String INSERT_QUERY = "INSERT INTO employees (name, birth_date, email, company_id)" +
                                               " VALUES (:name, :birth_date, :email, :company_id)";
    private static final String UPDATE_QUERY = "UPDATE employees" +
                                               " SET name=:name, birth_date=:birth_date, email=:email, company_id=:company_id" +
                                               " WHERE id=:id";
    private static final String SELECT_BY_ID_QUERY = "SELECT e.id emp_id, e.name emp_name, e.birth_date, e.email," +
                                                            " c.id comp_id, c.name comp_name, c.tin, c.address, c.phone_number " +
                                                     "FROM employees AS e " +
                                                     "LEFT JOIN companies AS c ON c.id = e.company_id " +
                                                     "WHERE e.id=:id";
    private static final String SELECT_ALL_QUERY = "SELECT e.id emp_id, e.name emp_name, e.birth_date, e.email," +
                                                   " c.id comp_id, c.name comp_name, c.tin, c.address, c.phone_number " +
                                                   "FROM employees AS e " +
                                                   "LEFT JOIN companies AS c ON c.id = e.company_id ";
    private static final String DELETE_QUERY = "DELETE FROM employees WHERE id=:id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public EmployeeRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Transactional
    public int save(Employee employee) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT_QUERY, getParameterMap(employee), keyHolder, new String[]{"id"});

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Transactional
    public boolean update(Employee employee) {
        return namedParameterJdbcTemplate.update(UPDATE_QUERY, getParameterMap(employee)) != 0;
    }

    private SqlParameterSource getParameterMap(Employee employee) {
        return new MapSqlParameterSource()
                .addValue("id", employee.getId())
                .addValue("name", employee.getName())
                .addValue("birth_date", employee.getBirthDate())
                .addValue("email", employee.getEmail())
                .addValue("company_id", employee.getCompany().getId());
    }

    public Employee get(int id) {
        return namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, new MapSqlParameterSource("id", id), getEmployeeMapper());
    }

    public List<Employee> getAll() {
        return namedParameterJdbcTemplate.query(SELECT_ALL_QUERY, getEmployeeMapper());
    }

    @Transactional
    public boolean delete(int id) {
        return namedParameterJdbcTemplate.update(DELETE_QUERY, new MapSqlParameterSource("id", id)) != 0;
    }
}
