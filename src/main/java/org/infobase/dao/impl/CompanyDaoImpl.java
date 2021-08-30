package org.infobase.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.jooq.DSLContext;
import org.jooq.InsertQuery;
import org.jooq.UpdateQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import org.infobase.model.Company;
import org.infobase.dao.CompanyDao;
import org.infobase.db.generated.tables.records.CompaniesRecord;

import static org.infobase.db.generated.Tables.COMPANIES;

@Repository
@Slf4j
@RequiredArgsConstructor
public class CompanyDaoImpl implements CompanyDao {

    private final DSLContext dslContext;

    @Transactional
    public int save(Company company) {
        int id = 0;
        try (InsertQuery<CompaniesRecord> insertQuery = dslContext.insertQuery(COMPANIES)) {
            insertQuery.setReturning(COMPANIES.ID);
            insertQuery.addValues(getMap(company));
            insertQuery.execute();
            id = insertQuery.getReturnedRecord().getId();
        } catch (Exception e) {
            log.error(e.toString());
        }
        return id;
    }

    @Transactional
    public int update(Company company) {
        int result = 0;
        try (UpdateQuery<CompaniesRecord> updateQuery = dslContext.updateQuery(COMPANIES)) {
            updateQuery.addValues(getMap(company));
            updateQuery.addConditions(COMPANIES.ID.eq(company.getId()));
            result = updateQuery.execute();
        } catch (Exception dae) {
            log.error(dae.toString());
        }
        return result;
    }

    private Map getMap(Company company) {
        return Map.of(
                "name", company.getName(),
                "tin", company.getTin(),
                "address", company.getAddress(),
                "phone_number", company.getPhoneNumber()
        );
    }

    public Company getById(int id) {
        Company result = null;
        try {
            result = dslContext.selectFrom(COMPANIES)
                    .where(COMPANIES.ID.eq(id))
                    .fetchOne()
                    .into(Company.class);
        } catch (Exception e) {
            log.error(e.toString());
        }
        return result;
    }

    public Company getByName(String name) {
        Company result = null;
        try {
            result = dslContext.selectFrom(COMPANIES)
                    .where(COMPANIES.NAME.eq(name))
                    .fetchOne()
                    .into(Company.class);
        } catch (Exception dae) {
            log.error(dae.toString());
        }
        return result;
    }

    public List<Company> getAll() {
        List<Company> result = null;
        try {
            result = dslContext.selectFrom(COMPANIES).fetch().into(Company.class);
        } catch (Exception dae) {
            log.error(dae.toString());
        }
        return result;
    }

    public List<Company> search(String textToSearch) {
        String pattern = "%" + textToSearch.toLowerCase(Locale.ROOT) + "%";
        List<Company> result = null;
        try {
            result = dslContext.selectFrom(COMPANIES)
                    .where(
                            COMPANIES.NAME.like(pattern)
                            .or(COMPANIES.TIN.like(pattern))
                            .or(COMPANIES.ADDRESS.like(pattern))
                            .or(COMPANIES.PHONE_NUMBER.like(pattern))
                    ).fetch()
                    .into(Company.class);
        } catch (Exception dae) {
            log.error(dae.toString());
        }
        return result;
    }

    @Transactional
    public boolean delete(int id) {
        boolean success = false;
        try {
            success = dslContext.deleteFrom(COMPANIES).where(COMPANIES.ID.eq(id)).execute() != 0;
        } catch (Exception dae) {
            log.error(dae.toString());
        }
        return success;
    }
}
