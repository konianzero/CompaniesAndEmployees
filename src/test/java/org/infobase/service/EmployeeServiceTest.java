package org.infobase.service;

import org.infobase.to.EmployeeTo;

import org.infobase.util.mapper.EmployeeMapper;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.infobase.EmployeeTestData.*;
import static org.junit.jupiter.api.Assertions.assertNull;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@Testcontainers
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeMapper mapper;

    @Test
    void get() {
        EmployeeTo actual = employeeService.get(EMPLOYEE_1_ID);
        EMPLOYEE_TO_MATCHER.assertMatch(actual, mapper.toEmployeeTo(EMPLOYEE_1));
    }

    @Test
    void getAll() {
        List<EmployeeTo> actual = employeeService.getAll();
        EMPLOYEE_TO_MATCHER.assertMatch(actual, mapper.toEmployeeToList(ALL_EMPLOYEES));
    }

    @Test
    void save() {
        EmployeeTo newEmployeeTo = mapper.toEmployeeTo(getNew());
        int id = employeeService.saveOrUpdate(newEmployeeTo);
        newEmployeeTo.setId(id);
        EMPLOYEE_TO_MATCHER.assertMatch(employeeService.get(id), newEmployeeTo);
    }

    @Test
    void update() {
        EmployeeTo updatedTo = mapper.toEmployeeTo(getUpdated());
        employeeService.saveOrUpdate(updatedTo);
        EMPLOYEE_TO_MATCHER.assertMatch(employeeService.get(EMPLOYEE_1_ID), updatedTo);
    }

    @Test
    void delete() {
        employeeService.delete(EMPLOYEE_1_ID);
        assertNull(employeeService.get(EMPLOYEE_1_ID));
    }
}