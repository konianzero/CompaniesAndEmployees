package org.infobase.service;

import org.infobase.model.Employee;
import org.infobase.to.EmployeeTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import java.util.List;

import static org.infobase.EmployeeTestData.*;
import static org.infobase.util.EmployeeUtil.createTo;
import static org.infobase.util.EmployeeUtil.createToList;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    void save() {
        Employee newEmployee = getNew();
        int id = employeeService.save(newEmployee);
        newEmployee.setId(id);
        EMPLOYEE_TO_MATCHER.assertMatch(employeeService.get(id), createTo(newEmployee));
    }

    @Test
    void update() {
        Employee updated = getUpdated();
        employeeService.update(updated);
        EMPLOYEE_TO_MATCHER.assertMatch(employeeService.get(EMPLOYEE_1_ID), createTo(updated));
    }

    @Test
    void get() {
        EmployeeTo actual = employeeService.get(EMPLOYEE_1_ID);
        EMPLOYEE_TO_MATCHER.assertMatch(actual, createTo(EMPLOYEE_1));
    }

    @Test
    void getAll() {
        List<EmployeeTo> actual = employeeService.getAll();
        EMPLOYEE_TO_MATCHER.assertMatch(actual, createToList(ALL_EMPLOYEES));
    }

    @Test
    void delete() {
        employeeService.delete(EMPLOYEE_1_ID);
        assertThrows(EmptyResultDataAccessException.class, () -> employeeService.get(EMPLOYEE_1_ID));
    }
}