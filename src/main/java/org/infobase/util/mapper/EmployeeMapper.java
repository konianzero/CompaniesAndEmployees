package org.infobase.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import org.infobase.model.Employee;
import org.infobase.to.EmployeeTo;

import java.util.List;

/**
 * Утилитный интерфейс для конвертирования объектов базы данных в объекты отображения и наоборот
 */
@Mapper(componentModel="spring")
public interface EmployeeMapper {
    /** Экземпляр класса отображения */
    EmployeeMapper  INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    /**
     * Конвертирует сущность сотрудника из базы данных в сущность для отображения в интерфейсе
     * @param employee сущность сотрудника из базы данных
     * @return сущность сотрудника для отображения в интерфейсе
     */
    @Mapping(target = "companyName", source = "employee.company.name")
    EmployeeTo toEmployeeTo(Employee employee);

    /**
     * Конвертирует сущность для отображения в интерфейсе в сущность сотрудника для базы данных
     * @param employeeTo сущность сотрудника для отображения в интерфейсе
     * @return сущность сотрудника для базы данных
     */
    Employee toEmployee(EmployeeTo employeeTo);

    /**
     * Конвертирует список сущностей сотрудников из базы данных в список сущностей для отображения в интерфейсе
     * @param employees список сущностей сотрудников из базы данных
     * @return список сущностей сотрудников из базы данных
     */
    List<EmployeeTo> toEmployeeToList(List<Employee> employees);
}
