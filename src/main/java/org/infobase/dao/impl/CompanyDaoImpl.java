package org.infobase.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.jooq.DSLContext;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import org.infobase.model.Company;
import org.infobase.dao.mappers.CompanyMapper;
import org.infobase.dao.CompanyDao;

@Repository
@Slf4j
@RequiredArgsConstructor
public class CompanyDaoImpl implements CompanyDao {

    private static final String INSERT_QUERY = "INSERT INTO companies (name, tin, address, phone_number)" +
                                               " VALUES (:name, :tin, :address, :phone_number)";
    private static final String UPDATE_QUERY = "UPDATE companies" +
                                               " SET name=:name, tin=:tin, address=:address, phone_number=:phone_number" +
                                               " WHERE id=:id";
    private static final String SELECT_BY_ID_QUERY = "SELECT id, name, tin, address, phone_number FROM companies WHERE id=:id";
    private static final String SELECT_BY_NAME_QUERY = "SELECT id, name, tin, address, phone_number FROM companies WHERE name=:name";
    private static final String SELECT_ALL_QUERY = "SELECT id, name, tin, address, phone_number FROM companies";
    private static final String SEARCH_QUERY = "SELECT id, name, tin, address, phone_number FROM companies " +
                                               "WHERE lower(name) LIKE :search " +
                                               "OR lower(tin) LIKE :search " +
                                               "OR lower(address) LIKE :search " +
                                               "OR lower(phone_number) LIKE :search ";
    private static final String DELETE_QUERY = "DELETE FROM companies WHERE id=:id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Transactional
    public int save(Company company) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            namedParameterJdbcTemplate.update(INSERT_QUERY, getParameterMap(company), keyHolder, new String[]{"id"});
        } catch (Exception dae) {
            log.error(dae.toString());
        }
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Transactional
    public int update(Company company) {
        int result = 0;
        try {
            result = namedParameterJdbcTemplate.update(UPDATE_QUERY, getParameterMap(company));
        } catch (Exception dae) {
            log.error(dae.toString());
        }
        return result;
    }

    private SqlParameterSource getParameterMap(Company company) {
        return new MapSqlParameterSource()
                .addValue("id", company.getId())
                .addValue("name", company.getName())
                .addValue("tin", company.getTin())
                .addValue("address", company.getAddress())
                .addValue("phone_number", company.getPhoneNumber());
    }

    public Company getById(int id) {
        Company result = null;
        try {
            result = namedParameterJdbcTemplate.queryForObject(
                    SELECT_BY_ID_QUERY,
                    new MapSqlParameterSource("id", id),
                    new CompanyMapper()
            );
        } catch (Exception dae) {
            log.error(dae.toString());
        }
        return result;
    }

    public Company getByName(String name) {
        Company result = null;
        try {
            result = namedParameterJdbcTemplate.queryForObject(
                    SELECT_BY_NAME_QUERY,
                    new MapSqlParameterSource("name", name),
                    new CompanyMapper()
            );
        } catch (Exception dae) {
            log.error(dae.toString());
        }
        return result;
    }

    public List<Company> getAll() {
        List<Company> result = null;
        try {
            result = namedParameterJdbcTemplate.query(SELECT_ALL_QUERY, new CompanyMapper());
        } catch (Exception dae) {
            log.error(dae.toString());
        }
        return result;
    }

    public List<Company> search(String textToSearch) {
        List<Company> result = null;
        try {
            result = namedParameterJdbcTemplate.query(
                    SEARCH_QUERY,
                    new MapSqlParameterSource("search", "%" + textToSearch.toLowerCase() + "%"),
                    new CompanyMapper()
            );
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
