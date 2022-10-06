package org.infobase.service;

import org.infobase.to.EmployeeTo;

import org.infobase.util.mapper.EmployeeMapper;
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

import static org.infobase.EmployeeTestData.*;
import static org.junit.jupiter.api.Assertions.assertNull;

@ActiveProfiles("test")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testcontainers
class EmployeeServiceTest {

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
    private EmployeeService employeeService;
    @Autowired
    private EmployeeMapper mapper;

    @Test
    @Order(2)
    void get() {
        EmployeeTo actual = employeeService.get(EMPLOYEE_1_ID);
        EMPLOYEE_TO_MATCHER.assertMatch(actual, mapper.toEmployeeTo(EMPLOYEE_1));
    }

    @Test
    @Order(3)
    void getAll() {
        List<EmployeeTo> actual = employeeService.getAll();
        EMPLOYEE_TO_MATCHER.assertMatch(actual, mapper.toEmployeeToList(ALL_EMPLOYEES));
    }

    @Test
    @Order(4)
    void save() {
        EmployeeTo newEmployeeTo = mapper.toEmployeeTo(getNew());
        int id = employeeService.saveOrUpdate(newEmployeeTo);
        newEmployeeTo.setId(id);
        EMPLOYEE_TO_MATCHER.assertMatch(employeeService.get(id), newEmployeeTo);
    }

    @Test
    @Order(5)
    void update() {
        EmployeeTo updatedTo = mapper.toEmployeeTo(getUpdated());
        employeeService.saveOrUpdate(updatedTo);
        EMPLOYEE_TO_MATCHER.assertMatch(employeeService.get(EMPLOYEE_1_ID), updatedTo);
    }

    @Test
    @Order(6)
    void delete() {
        employeeService.delete(EMPLOYEE_1_ID);
        assertNull(employeeService.get(EMPLOYEE_1_ID));
    }
}