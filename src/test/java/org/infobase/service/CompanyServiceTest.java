package org.infobase.service;

import org.infobase.model.Company;

import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.infobase.CompanyTestData.*;
import static org.junit.jupiter.api.Assertions.assertNull;


@ActiveProfiles("test")
@SpringBootTest
@Transactional
@Testcontainers
class CompanyServiceTest {

    @Autowired
    private CompanyService companyService;

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
    void save() {
        Company newCompany = getNew();
        int id = companyService.saveOrUpdate(newCompany);
        newCompany.setId(id);
        COMPANY_MATCHER.assertMatch(companyService.get(id), newCompany);
    }

    @Test
    void update() {
        Company updated = getUpdated();
        companyService.saveOrUpdate(updated);
        COMPANY_MATCHER.assertMatch(companyService.get(COMPANY_1_ID), updated);
    }

    @Test
    void delete() {
        companyService.delete(COMPANY_1_ID);
        assertNull(companyService.get(COMPANY_1_ID));
    }
}