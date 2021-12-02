package org.infobase.dao;

import org.infobase.model.Employee;
import org.infobase.web.component.grid.EmployeeHeaders;

import java.util.List;

/**
 * Интерфейс доступа к данным сотрудников
 */
public interface EmployeeDao {
    /**
     * Сохранение сущности нового сотрудника в базе
     * @param employee сущность сотрудника для отображения данных из базы
     * @return 0 - если операция не выполнена;
     *         число больше нуля если операция прошла успешно
     */
    int save(Employee employee);

    /**
     * Обновление существующих сотрудников в базе
     * @param employee сущность сотрудника для отображения данных из базы
     * @return 0 - если операция не выполнена;
     *         число больше нуля если операция прошла успешно
     */
    int update(Employee employee);

    /**
     * Получить сущность сотрудника по ID из базы
     * @param id ID сущности сотрудника
     * @return сущность сотрудника для отображения в интерфейсе
     */
    Employee get(int id);

    /**
     * Получение списка сущностей всех сотрудников из базы
     * @return список сущностей(для отображения в интерфейсе) всех сотрудников
     */
    List<Employee> getAll();

    /**
     * Поиск сущностей сотрудников в базе
     * @param columnName название колонки таблицы для поиска
     * @param textToSearch текст поиска
     * @return список сущностей(для отображения в интерфейсе) сотрудников подходящих по параметрам поиска
     */
    List<Employee> search(EmployeeHeaders columnName, String textToSearch);

    /**
     * Удаление сущности сотрудника из базы
     * @param id ID сущности сотрудника
     * @return true - если операция выполнена успешно;
     *         false - если операция не выполнена
     */
    boolean delete(int id);
}
