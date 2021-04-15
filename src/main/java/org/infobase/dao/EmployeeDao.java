package org.infobase.dao;

import org.infobase.model.Employee;
import org.infobase.web.component.grid.EmployeeHeaders;

import java.util.List;

public interface EmployeeDao {
    int save(Employee employee);
    int update(Employee employee);
    Employee get(int id);
    List<Employee> getAll();
    List<Employee> search(EmployeeHeaders columnName, String textToSearch);
    boolean delete(int id);
}
