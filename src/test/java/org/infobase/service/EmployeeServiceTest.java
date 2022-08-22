package org.infobase.service;

import org.infobase.to.EmployeeTo;

import org.infobase.util.mapper.EmployeeMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.infobase.EmployeeTestData.*;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql("/db/data.sql")
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeMapper mapper;

    @Test
    @Order(1)
    void get() {
        EmployeeTo actual = employeeService.get(EMPLOYEE_1_ID);
        EMPLOYEE_TO_MATCHER.assertMatch(actual, mapper.toEmployeeTo(EMPLOYEE_1));
    }

    @Test
    @Order(2)
    void getAll() {
        List<EmployeeTo> actual = employeeService.getAll();
        EMPLOYEE_TO_MATCHER.assertMatch(actual, mapper.toEmployeeToList(ALL_EMPLOYEES));
    }

    @Test
    @Order(3)
    void save() {
        EmployeeTo newEmployeeTo = mapper.toEmployeeTo(getNew());
        int id = employeeService.saveOrUpdate(newEmployeeTo);
        newEmployeeTo.setId(id);
        EMPLOYEE_TO_MATCHER.assertMatch(employeeService.get(id), newEmployeeTo);
    }

    @Test
    @Order(4)
    void update() {
        EmployeeTo updatedTo = mapper.toEmployeeTo(getUpdated());
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