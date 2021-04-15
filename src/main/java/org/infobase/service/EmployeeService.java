package org.infobase.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;

import org.infobase.model.Employee;
import org.infobase.dao.impl.CompanyDaoImpl;
import org.infobase.dao.impl.EmployeeDaoImpl;
import org.infobase.to.EmployeeTo;
import org.infobase.util.EmployeeUtil;

import org.infobase.web.component.grid.EmployeeHeaders;

@Service
@Slf4j
public class EmployeeService {

    private final EmployeeDaoImpl employeeDaoImpl;
    private final CompanyDaoImpl companyDaoImpl;

    public EmployeeService(EmployeeDaoImpl employeeDaoImpl, CompanyDaoImpl companyDaoImpl) {
        this.employeeDaoImpl = employeeDaoImpl;
        this.companyDaoImpl = companyDaoImpl;
    }

    public int saveOrUpdate(EmployeeTo employeeTo) {
        Employee employee = EmployeeUtil.createEntity(employeeTo);
        employee.setCompany(companyDaoImpl.getByName(employeeTo.getCompanyName()));

        if (employee.getId() == null) {
            log.info("Save {}", employeeTo);
            return employeeDaoImpl.save(employee);
        }
        log.info("Save {}", employeeTo);
        return employeeDaoImpl.update(employee);
    }

    public EmployeeTo get(int id) {
        log.info("Get employee with id:{}", id);
        return EmployeeUtil.createTo(employeeDaoImpl.get(id));
    }

    public List<EmployeeTo> getAll() {
        log.info("Get all employees");
        return EmployeeUtil.createToList(employeeDaoImpl.getAll());
    }

    public List<EmployeeTo> search(EmployeeHeaders columnName, String textToSearch) {
        log.info("Search employees by field '{}' with \"{}\"", columnName.getHeader(), textToSearch);
        return EmployeeUtil.createToList(employeeDaoImpl.search(columnName, textToSearch));
    }

    public boolean delete(int id) {
        log.info("Delete employee with id:{}", id);
        return employeeDaoImpl.delete(id);
    }
}
