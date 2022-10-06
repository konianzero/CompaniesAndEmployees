package org.infobase.service;

import org.infobase.model.Company;

import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.infobase.CompanyTestData.*;
import static org.junit.jupiter.api.Assertions.assertNull;


@ActiveProfiles("test")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testcontainers
class CompanyServiceTest {

    @Container
    private static PostgreSQLContainer<?> postgresDB = new PostgreSQLContainer<>("postgres:10").withDatabaseName("test_db");

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",postgresDB::getJdbcUrl);
        registry.add("spring.datasource.username", postgresDB::getUsername);
        registry.add("spring.datasource.password", postgresDB::getPassword);
    }

    @Test
    @Order(1)
    @DisplayName("start container")
    void test() {
        Assertions.assertTrue(postgresDB.isRunning());
    }

    @Autowired
    private CompanyService companyService;

    @Test
    @Order(2)
    void get() {
        Company actual = companyService.get(COMPANY_1_ID);
        COMPANY_MATCHER.assertMatch(actual, COMPANY_1);
    }

    @Test
    @Order(3)
    void getAll() {
        List<Company> actual = companyService.getAll();
        COMPANY_MATCHER.assertMatch(actual, ALL_COMPANIES);
    }

    @Test
    @Order(4)
    void save() {
        Company newCompany = getNew();
        int id = companyService.saveOrUpdate(newCompany);
        newCompany.setId(id);
        COMPANY_MATCHER.assertMatch(companyService.get(id), newCompany);
    }

    @Test
    @Order(5)
    void update() {
        Company updated = getUpdated();
        companyService.saveOrUpdate(updated);
        COMPANY_MATCHER.assertMatch(companyService.get(COMPANY_1_ID), updated);
    }

    @Test
    @Order(6)
    void delete() {
        companyService.delete(COMPANY_1_ID);
        assertNull(companyService.get(COMPANY_1_ID));
    }
}