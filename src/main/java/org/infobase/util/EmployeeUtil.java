package org.infobase.util;

import java.util.List;
import java.util.stream.Collectors;

import org.infobase.model.Employee;
import org.infobase.to.EmployeeTo;
import org.infobase.util.mapper.EmployeeMapper;

public class EmployeeUtil {

    public static EmployeeTo createTo(Employee employee) {
        return EmployeeMapper.INSTANCE.toEmployeeTo(employee);
    }

    public static List<EmployeeTo> createToList(List<Employee> employees) {
        return employees.stream()
                        .map(EmployeeUtil::createTo)
                        .collect(Collectors.toList());
    }

    public static Employee createEntity(EmployeeTo employeeTo) {
        return EmployeeMapper.INSTANCE.toEmployee(employeeTo);
    }
}
