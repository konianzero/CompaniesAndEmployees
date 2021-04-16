package org.infobase.util;

import java.util.List;
import java.util.stream.Collectors;

import org.infobase.model.Employee;
import org.infobase.to.EmployeeTo;
import org.infobase.util.mapper.EmployeeMapper;

/**
 * Утилитный класс для сущности сотрудника
 */
public class EmployeeUtil {

    private EmployeeUtil() { }

    /**
     * Создаёт сущность сотрудника для отображения в интерфейсе
     * @param employee сущность сотрудника из базы данных
     * @return сущность сотрудника для отображения в интерфейсе
     */
    public static EmployeeTo createTo(Employee employee) {
        return EmployeeMapper.INSTANCE.toEmployeeTo(employee);
    }

    /**
     * Создаёт список сущностей сотрудников для отображения в интерфейсе
     * @param employees список сущностей сотрудников из базы данных
     * @return список сущностей сотрудников для отображения в интерфейсе
     */
    public static List<EmployeeTo> createToList(List<Employee> employees) {
        return employees.stream()
                        .map(EmployeeUtil::createTo)
                        .collect(Collectors.toList());
    }

    /**
     * Создаёт сущность сотрудника для базы данных
     * @param employeeTo сущность сотрудника для отображения в интерфейсе
     * @return сущность сотрудника для базы данных
     */
    public static Employee createEntity(EmployeeTo employeeTo) {
        return EmployeeMapper.INSTANCE.toEmployee(employeeTo);
    }
}
