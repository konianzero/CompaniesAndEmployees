package org.infobase.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
import org.infobase.dao.mappers.EmployeeMapper;
import org.infobase.dao.EmployeeDao;
import org.infobase.web.component.grid.EmployeeHeaders;

@Repository
@Slf4j
@RequiredArgsConstructor
public class EmployeeDaoImpl implements EmployeeDao {

    private static final String INSERT_QUERY = "INSERT INTO employees (name, birth_date, email, company_id)" +
                                               " VALUES (:name, :birth_date, :email, :company_id)";
    private static final String UPDATE_QUERY = "UPDATE employees" +
                                               " SET name=:name, birth_date=:birth_date, email=:email, company_id=:company_id" +
                                               " WHERE id=:id";
    private static final String SELECT_ALL_QUERY = "SELECT e.id emp_id, e.name emp_name, e.birth_date, e.email," +
                                                   " c.id comp_id, c.name comp_name, c.tin, c.address, c.phone_number " +
                                                   "FROM employees AS e " +
                                                   "LEFT JOIN companies AS c ON c.id = e.company_id ";
    private static final String SELECT_BY_ID_QUERY = SELECT_ALL_QUERY + "WHERE e.id=:id";
    private static final String SEARCH_QUERY = SELECT_ALL_QUERY + "WHERE %s";
    private static final String DELETE_QUERY = "DELETE FROM employees WHERE id=:id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Transactional
    public int save(Employee employee) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            namedParameterJdbcTemplate.update(INSERT_QUERY, getParameterMap(employee), keyHolder, new String[]{"id"});
        } catch (Exception dae) {
            log.error(dae.toString());
        }
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Transactional
    public int update(Employee employee) {
        int result = 0;
        try {
            result = namedParameterJdbcTemplate.update(UPDATE_QUERY, getParameterMap(employee));
        } catch (Exception dae) {
            log.error(dae.toString());
        }
        return result;
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
        Employee result = null;
        try {
            result = namedParameterJdbcTemplate.queryForObject(
                    SELECT_BY_ID_QUERY,
                    new MapSqlParameterSource("id", id),
                    new EmployeeMapper()
            );
        } catch (Exception dae) {
            log.error(dae.toString());
        }
        return result;
    }

    public List<Employee> getAll() {
        List<Employee> result = null;
        try {
            result = namedParameterJdbcTemplate.query(SELECT_ALL_QUERY, new EmployeeMapper());
        } catch (Exception dae) {
            log.error(dae.toString());
        }
        return result;
    }

    public List<Employee> search(EmployeeHeaders columnName, String textToSearch) {
        String sql;
        SqlParameterSource map;
        switch (columnName) {
            case FULL_NAME:
                sql = String.format(SEARCH_QUERY, "lower(e.name) LIKE :search");
                map = new MapSqlParameterSource("search", "%" + textToSearch.toLowerCase() + "%");
                break;
            case BIRTH_DATE:
                sql = String.format(SEARCH_QUERY, "e.birth_date=:birth_date");
                map = new MapSqlParameterSource("birth_date", LocalDate.parse(textToSearch));
                break;
            case EMAIL:
                sql = String.format(SEARCH_QUERY, "lower(e.email) LIKE :search");
                map = new MapSqlParameterSource("search", "%" + textToSearch.toLowerCase() + "%");
                break;
            case COMPANY:
                sql = String.format(SEARCH_QUERY, "c.name=:name");
                map = new MapSqlParameterSource("name", textToSearch);
                break;
            default:
                return getAll();
        }


        List<Employee> result = null;
        try {
            result = namedParameterJdbcTemplate.query(sql, map, new EmployeeMapper());
        } catch (Exception dae) {
            log.error(dae.toString());
        }
        return result;
    }

    @Transactional
    public boolean delete(int id) {
        boolean result = false;
        try {
            result = namedParameterJdbcTemplate.update(DELETE_QUERY, new MapSqlParameterSource("id", id)) != 0;
        } catch (Exception dae) {
            log.error(dae.toString());
        }
        return result;
    }
}
