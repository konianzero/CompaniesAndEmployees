package org.infobase.repository;

import org.infobase.model.Company;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository
public class CompanyRepository {

    private static final String INSERT_QUERY = "INSERT INTO companies (name, tin, address, phone_number)" +
                                               " VALUES (:name, :tin, :address, :phone_number)";
    private static final String UPDATE_QUERY = "UPDATE companies" +
                                               " SET name=:name, tin=:tin, address=:address, phone_number=:phone_number" +
                                               " WHERE id=:id";
    private static final String SELECT_BY_ID_QUERY = "SELECT id, name, tin, address, phone_number FROM companies WHERE id=:id";
    private static final String SELECT_ALL_QUERY = "SELECT id, name, tin, address, phone_number FROM companies";
    private static final String DELETE_QUERY = "DELETE FROM companies WHERE id=:id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CompanyRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Transactional
    public int save(Company company) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", company.getId())
                .addValue("name", company.getName())
                .addValue("tin", company.getTin())
                .addValue("address", company.getAddress())
                .addValue("phone_number", company.getPhoneNumber());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT_QUERY, map, keyHolder, new String[]{"idy"});

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Transactional
    public boolean update(Company company) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", company.getId())
                .addValue("name", company.getName())
                .addValue("tin", company.getTin())
                .addValue("address", company.getAddress())
                .addValue("phone_number", company.getPhoneNumber());
        return namedParameterJdbcTemplate.update(UPDATE_QUERY, map) != 0;
    }

    public Company get(int id) {
        return namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, new MapSqlParameterSource("id", id), new CompanyMapper());
    }

    public List<Company> getAll() {
        return namedParameterJdbcTemplate.query(SELECT_ALL_QUERY, new CompanyMapper());
    }

    @Transactional
    public boolean delete(int id) {
        return namedParameterJdbcTemplate.update(DELETE_QUERY, new MapSqlParameterSource("id", id)) != 0;
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
