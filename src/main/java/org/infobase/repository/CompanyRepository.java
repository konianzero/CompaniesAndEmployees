package org.infobase.repository;

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

import static org.infobase.util.Util.getCompanyMapper;

@Repository
public class CompanyRepository {

    private static final String INSERT_QUERY = "INSERT INTO companies (name, tin, address, phone_number)" +
                                               " VALUES (:name, :tin, :address, :phone_number)";
    private static final String UPDATE_QUERY = "UPDATE companies" +
                                               " SET name=:name, tin=:tin, address=:address, phone_number=:phone_number" +
                                               " WHERE id=:id";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM companies WHERE id=:id";
    private static final String SELECT_BY_NAME_QUERY = "SELECT * FROM companies WHERE name=:name";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM companies";
    private static final String DELETE_QUERY = "DELETE FROM companies WHERE id=:id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CompanyRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Transactional
    public int save(Company company) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT_QUERY, getParameterMap(company), keyHolder, new String[]{"id"});

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Transactional
    public int update(Company company) {
        return namedParameterJdbcTemplate.update(UPDATE_QUERY, getParameterMap(company));
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
        return namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, new MapSqlParameterSource("id", id), getCompanyMapper());
    }

    public Company getByName(String name) {
        return namedParameterJdbcTemplate.queryForObject(SELECT_BY_NAME_QUERY, new MapSqlParameterSource("name", name), getCompanyMapper());
    }

    public List<Company> getAll() {
        return namedParameterJdbcTemplate.query(SELECT_ALL_QUERY, getCompanyMapper());
    }

    @Transactional
    public boolean delete(int id) {
        return namedParameterJdbcTemplate.update(DELETE_QUERY, new MapSqlParameterSource("id", id)) != 0;
    }
}
