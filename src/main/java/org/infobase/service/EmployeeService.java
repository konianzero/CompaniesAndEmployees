package org.infobase.service;

import org.infobase.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import org.infobase.model.Employee;
import org.infobase.repository.EmployeeRepository;
import org.infobase.to.EmployeeTo;
import org.infobase.util.EmployeeUtil;

@Service
public class EmployeeService {

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
            return employeeRepository.save(employee);
        }
        return employeeRepository.update(employee);
    }

    public EmployeeTo get(int id) {
        return EmployeeUtil.createTo(employeeRepository.get(id));
    }

    public List<EmployeeTo> getAll() {
        return EmployeeUtil.createToList(employeeRepository.getAll());
    }

    public void delete(int id) {
        employeeRepository.delete(id);
    }
}
