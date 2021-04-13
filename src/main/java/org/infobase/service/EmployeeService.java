package org.infobase.service;

import org.infobase.repository.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import org.infobase.model.Employee;
import org.infobase.repository.EmployeeRepository;
import org.infobase.to.EmployeeTo;
import org.infobase.util.EmployeeUtil;

@Service
public class EmployeeService {
    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;

    public EmployeeService(EmployeeRepository employeeRepository, CompanyRepository companyRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
    }

    public int saveOrUpdate(EmployeeTo employeeTo) {
        Employee employee = EmployeeUtil.createEntity(employeeTo);
        employee.setCompany(companyRepository.getByName(employeeTo.getCompanyName()));

        if (employee.getId() == null) {
            log.info("Save {}", employeeTo);
            return employeeRepository.save(employee);
        }
        log.info("Save {}", employeeTo);
        return employeeRepository.update(employee);
    }

    public EmployeeTo get(int id) {
        log.info("Get employee with id:{}", id);
        return EmployeeUtil.createTo(employeeRepository.get(id));
    }

    public List<EmployeeTo> getAll() {
        log.debug("Get all employees");
        return EmployeeUtil.createToList(employeeRepository.getAll());
    }

    public List<EmployeeTo> search(String columnName, String textToSearch) {
        log.info("Search employees by field '{}' with \"{}\"", columnName, textToSearch);
        return EmployeeUtil.createToList(employeeRepository.search(columnName, textToSearch.toLowerCase()));
    }

    public void delete(int id) {
        log.info("Delete employee with id:{}", id);
        employeeRepository.delete(id);
    }
}
