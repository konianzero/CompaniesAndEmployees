package org.infobase.service;

import org.infobase.to.EmployeeTo;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import java.util.List;

import static org.infobase.EmployeeTestData.*;
import static org.infobase.util.EmployeeUtil.createTo;
import static org.infobase.util.EmployeeUtil.createToList;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    @Order(1)
    void get() {
        EmployeeTo actual = employeeService.get(EMPLOYEE_1_ID);
        EMPLOYEE_TO_MATCHER.assertMatch(actual, createTo(EMPLOYEE_1));
    }

    @Test
    @Order(2)
    void getAll() {
        List<EmployeeTo> actual = employeeService.getAll();
        EMPLOYEE_TO_MATCHER.assertMatch(actual, createToList(ALL_EMPLOYEES));
    }

    @Test
    @Order(3)
    void save() {
        EmployeeTo newEmployeeTo = createTo(getNew());
        int id = employeeService.saveOrUpdate(newEmployeeTo);
        newEmployeeTo.setId(id);
        EMPLOYEE_TO_MATCHER.assertMatch(employeeService.get(id), newEmployeeTo);
    }

    @Test
    @Order(4)
    void update() {
        EmployeeTo updatedTo = createTo(getUpdated());
        employeeService.saveOrUpdate(updatedTo);
        EMPLOYEE_TO_MATCHER.assertMatch(employeeService.get(EMPLOYEE_1_ID), updatedTo);
    }

    @Test
    @Order(5)
    void delete() {
        employeeService.delete(EMPLOYEE_1_ID);
        assertNull(employeeService.get(EMPLOYEE_1_ID));
    }
}