package org.infobase.service;

import org.infobase.model.Company;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

import static org.infobase.CompanyTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CompanyServiceTest extends AbstractServiceTest {

    @Autowired
    private CompanyService companyService;

    @Test
    void save() {
        Company newCompany = getNew();
        int id = companyService.save(newCompany);
        newCompany.setId(id);
        COMPANY_MATCHER.assertMatch(companyService.get(id), newCompany);
    }

    @Test
    void update() {
        Company updated = getUpdated();
        companyService.update(updated);
        COMPANY_MATCHER.assertMatch(companyService.get(COMPANY_1_ID), updated);
    }

    @Test
    void get() {
        Company actual = companyService.get(COMPANY_1_ID);
        COMPANY_MATCHER.assertMatch(actual, COMPANY_1);
    }

    @Test
    void getAll() {
        List<Company> actual = companyService.getAll();
        COMPANY_MATCHER.assertMatch(actual, ALL_COMPANIES);
    }

    @Test
    void delete() {
        companyService.delete(COMPANY_1_ID);
        assertThrows(EmptyResultDataAccessException.class, () -> companyService.get(COMPANY_1_ID));
    }
}