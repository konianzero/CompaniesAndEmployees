package org.infobase.service;

import org.infobase.CompanyTestData;
import org.infobase.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

import static org.infobase.EmployeeTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    void save() {
        Employee newEmployee = getNew();
        int id = employeeService.save(newEmployee);
        newEmployee.setId(id);
        EMPLOYEE_MATCHER.assertMatch(employeeService.get(id), newEmployee);
    }

    @Test
    void update() {
        Employee updated = getUpdated();
        employeeService.update(updated);
        EMPLOYEE_MATCHER.assertMatch(employeeService.get(EMPLOYEE_1_ID), updated);
    }

    @Test
    void get() {
        Employee actual = employeeService.get(EMPLOYEE_1_ID);
        EMPLOYEE_MATCHER.assertMatch(actual, EMPLOYEE_1);
        CompanyTestData.COMPANY_MATCHER.assertMatch(actual.getCompany(), CompanyTestData.COMPANY_1);
    }

    @Test
    void getAll() {
        List<Employee> actual = employeeService.getAll();
        EMPLOYEE_MATCHER.assertMatch(actual, ALL_EMPLOYEES);
    }

    @Test
    void delete() {
        employeeService.delete(EMPLOYEE_1_ID);
        assertThrows(EmptyResultDataAccessException.class, () -> employeeService.get(EMPLOYEE_1_ID));
    }
}