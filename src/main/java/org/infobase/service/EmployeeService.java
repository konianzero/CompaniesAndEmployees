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

/**
 * Класс бизнес логики для сущностей сотрудников
 */
@Service
@Slf4j
public class EmployeeService {

    /** Класс доступа к данным сотрудников */
    private final EmployeeDaoImpl employeeDaoImpl;
    /** Класс доступа к данным компаний */
    private final CompanyDaoImpl companyDaoImpl;

    /**
     * Создание класс бизнес логики для сущностей сотрудников
     * @param employeeDaoImpl Класс доступа к данным сотрудников
     * @param companyDaoImpl Класс доступа к данным компаний
     */
    public EmployeeService(EmployeeDaoImpl employeeDaoImpl, CompanyDaoImpl companyDaoImpl) {
        this.employeeDaoImpl = employeeDaoImpl;
        this.companyDaoImpl = companyDaoImpl;
    }

    /**
     * Сохранение сущности нового сотрудника или обновление существующей
     * @param employeeTo сущность сотрудника для отображения в интерфейсе
     * @return 0 - если операция не выполнена;
     *         число больше нуля если операция прошла успешно
     */
    public int saveOrUpdate(EmployeeTo employeeTo) {
        Employee employee = EmployeeUtil.createEntity(employeeTo);
        employee.setCompany(companyDaoImpl.getByName(employeeTo.getCompanyName()));

        if (employee.isNew()) {
            log.info("Save {}", employeeTo);
            return employeeDaoImpl.save(employee);
        }
        log.info("Update {}", employeeTo);
        return employeeDaoImpl.update(employee);
    }

    /**
     * Получить сущность сотрудника по ID
     * @param id ID сущности сотрудника
     * @return сущность сотрудника для отображения в интерфейсе
     */
    public EmployeeTo get(int id) {
        log.info("Get employee with id:{}", id);
        return EmployeeUtil.createTo(employeeDaoImpl.get(id));
    }

    /**
     * Получение списка сущностей всех сотрудников
     * @return список сущностей(для отображения в интерфейсе) всех сотрудников
     */
    public List<EmployeeTo> getAll() {
        log.info("Get all employees");
        return EmployeeUtil.createToList(employeeDaoImpl.getAll());
    }

    /**
     * Поиск сущностей сотрудников
     * @param columnName название колонки таблицы для поиска
     * @param textToSearch текст поиска
     * @return список сущностей(для отображения в интерфейсе) сотрудников подходящих по параметрам поиска
     */
    public List<EmployeeTo> search(EmployeeHeaders columnName, String textToSearch) {
        log.info("Search employees by field '{}' with \"{}\"", columnName.getHeader(), textToSearch);
        return EmployeeUtil.createToList(employeeDaoImpl.search(columnName, textToSearch));
    }

    /**
     * Удаление сущности сотрудника
     * @param id ID сущности сотрудника
     * @return true - если операция выполнена успешно;
     *         false - если операция не выполнена
     */
    public boolean delete(int id) {
        log.info("Delete employee with id:{}", id);
        return employeeDaoImpl.delete(id);
    }
}
