package org.infobase.service;

import org.infobase.model.Employee;
import org.infobase.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Employee get(int id) {
        return employeeRepository.get(id);
    }

    public List<Employee> getAll() {
        return employeeRepository.getAll();
    }

    public void delete(int id) {
        employeeRepository.delete(id);
    }
}
