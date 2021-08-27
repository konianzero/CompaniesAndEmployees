package org.infobase.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import org.infobase.model.Company;
import org.infobase.dao.CompanyDao;

import static org.infobase.db.generated.Tables.COMPANIES;

@Repository
@Slf4j
@RequiredArgsConstructor
public class CompanyDaoImpl implements CompanyDao {

    private final DSLContext dslContext;

    @Transactional
    public int save(Company company) {
        int id = 0;
        try {
            /* TODO - Don't insert, cause - ?
            InsertQuery<CompaniesRecord> insertQuery = dslContext.insertQuery(COMPANIES);
            insertQuery.addValues(getMap(company));
            id = insertQuery.getReturnedRecord().into(Company.class).getId();
            */
            // https://www.jooq.org/doc/latest/manual/sql-building/sql-statements/insert-statement/insert-values/
            id = dslContext.insertInto(COMPANIES)
                    .set(COMPANIES.NAME, company.getName())
                    .set(COMPANIES.TIN, company.getTin())
                    .set(COMPANIES.ADDRESS, company.getAddress())
                    .set(COMPANIES.PHONE_NUMBER, company.getPhoneNumber())
                    .returning(COMPANIES.ID)
                    .fetchOptional()
                    .orElseThrow(() -> new DataAccessException("Error inserting entity: " + company.getId()))
                    .getId();
        } catch (Exception e) {
            log.error(e.toString());
        }
        return id;
    }

    @Transactional
    public int update(Company company) {
        int result = 0;
        try {
            /* TODO - Produce BadSqlGrammarException, cause - add '"public"."companies".' to column names
            UpdateQuery<CompaniesRecord> updateQuery = dslContext.updateQuery(COMPANIES);
            updateQuery.addValues(getMap(company));
            updateQuery.addConditions(COMPANIES.ID.eq(company.getId()));
            result = updateQuery.execute();
             */
            result = dslContext.update(COMPANIES)
                    .set(DSL.field( "name"), company.getName())
                    .set(DSL.field( "tin"), company.getTin())
                    .set(DSL.field( "address"), company.getAddress())
                    .set(DSL.field( "phone_number"), company.getPhoneNumber())
                    /* TODO - Produce BadSqlGrammarException, cause - add '"public"."companies".' to column names
                    .set(COMPANIES.NAME, company.getName())
                    .set(COMPANIES.TIN, company.getTin())
                    .set(COMPANIES.ADDRESS, company.getAddress())
                    .set(COMPANIES.PHONE_NUMBER, company.getPhoneNumber())
                     */
                    .where(COMPANIES.ID.eq(company.getId()))
                    .returning(COMPANIES.ID)
                    .fetchOptional()
                    .orElseThrow(() -> new DataAccessException("Error updating entity: " + company.getId()))
                    .getId();
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
