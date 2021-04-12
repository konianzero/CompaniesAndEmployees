package org.infobase.service;

import org.springframework.stereotype.Service;

import java.util.List;

import org.infobase.model.Employee;
import org.infobase.repository.EmployeeRepository;
import org.infobase.to.EmployeeTo;
import org.infobase.util.EmployeeUtil;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public int save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void update(Employee employee) {
        employeeRepository.update(employee);
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
