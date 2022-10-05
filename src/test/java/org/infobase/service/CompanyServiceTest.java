package org.infobase.service;

import org.infobase.model.Company;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.infobase.CompanyTestData.*;
import static org.junit.jupiter.api.Assertions.assertNull;


@ActiveProfiles("test")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CompanyServiceTest {

    @Autowired
    private CompanyService companyService;

    @Test
    @Order(1)
    void get() {
        Company actual = companyService.get(COMPANY_1_ID);
        COMPANY_MATCHER.assertMatch(actual, COMPANY_1);
    }

    @Test
    @Order(2)
    void getAll() {
        List<Company> actual = companyService.getAll();
        COMPANY_MATCHER.assertMatch(actual, ALL_COMPANIES);
    }

    @Test
    @Order(3)
    void save() {
        Company newCompany = getNew();
        int id = companyService.saveOrUpdate(newCompany);
        newCompany.setId(id);
        COMPANY_MATCHER.assertMatch(companyService.get(id), newCompany);
    }

    @Test
    @Order(4)
    void update() {
        Company updated = getUpdated();
        companyService.saveOrUpdate(updated);
        COMPANY_MATCHER.assertMatch(companyService.get(COMPANY_1_ID), updated);
    }

    @Test
    @Order(5)
    void delete() {
        companyService.delete(COMPANY_1_ID);
        assertNull(companyService.get(COMPANY_1_ID));
    }
}