package org.infobase.util;

import org.infobase.model.Employee;
import org.infobase.to.EmployeeTo;
import org.infobase.util.mapper.EmployeeMapper;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeUtil {

    public static EmployeeTo createTo(Employee employee) {
        return EmployeeMapper.INSTANCE.toEmployeeTo(employee);
    }

    public static List<EmployeeTo> createToList(List<Employee> dishes) {
        return dishes.stream()
                .map(EmployeeUtil::createTo)
                .collect(Collectors.toList());
    }
}
