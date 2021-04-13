package org.infobase.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.infobase.model.Employee;

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
    private static final String SEARCH_QUERY = "SELECT e.id emp_id, e.name AS emp_name, e.birth_date, e.email," +
                                               " c.id comp_id, c.name comp_name, c.tin, c.address, c.phone_number " +
                                               "FROM employees AS e " +
                                               "LEFT JOIN companies AS c ON c.id = e.company_id " +
                                               "WHERE %s";
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
    public int update(Employee employee) {
        return namedParameterJdbcTemplate.update(UPDATE_QUERY, getParameterMap(employee));
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

    public List<Employee> search(String columnName, String textToSearch) {
        String sql;
        SqlParameterSource map;
        switch (columnName) {
            case "name":
                sql = String.format(SEARCH_QUERY, "lower(e.name) LIKE :search");
                map = new MapSqlParameterSource("search", "%" + textToSearch + "%");
                break;
            case "birth_date":
                sql = String.format(SEARCH_QUERY, "e.birth_date=:birth_date");
                map = new MapSqlParameterSource("birth_date", LocalDate.parse(textToSearch));
                break;
            case "email":
                sql = String.format(SEARCH_QUERY, "lower(e.email) LIKE :search");
                map = new MapSqlParameterSource("search", "%" + textToSearch + "%");
                break;
            case "comp_name":
                sql = String.format(SEARCH_QUERY, "lower(c.name) LIKE :search");
                map = new MapSqlParameterSource("search", "%" + textToSearch + "%");
                break;
            default:
                return getAll();
        }
        return namedParameterJdbcTemplate.query(sql, map, getEmployeeMapper());
    }

    @Transactional
    public boolean delete(int id) {
        return namedParameterJdbcTemplate.update(DELETE_QUERY, new MapSqlParameterSource("id", id)) != 0;
    }
}
