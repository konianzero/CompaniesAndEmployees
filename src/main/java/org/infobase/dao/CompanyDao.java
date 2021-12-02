package org.infobase.dao;

import java.util.List;

import org.infobase.model.Company;

/**
 * Интерфейс доступа к данным компаний
 */
public interface CompanyDao {
    /**
     * Сохранение сущности новой компании в базе
     * @param company сущность компании для отображения данных из базы
     * @return 0 - если операция не выполнена;
     *         число больше нуля если операция прошла успешно
     */
    int save(Company company);

    /**
     * Обновление существующей компании в базе
     * @param company сущность компании для отображения данных из базы
     * @return 0 - если операция не выполнена;
     *         число больше нуля если операция прошла успешно
     */
    int update(Company company);

    /**
     * Получить сущность компании по ID из базы
     * @param id ID сущности компании
     * @return сущность компании
     */
    Company getById(int id);

    /**
     * Получить имена всех компаний из базы
     * @return список имен всех компаний
     */
    Company getByName(String name);

    /**
     * Получение списка сущностей всех компаний из базы
     * @return список сущностей все компаний
     */
    List<Company> getAll();

    /**
     * Поиск сущностей компаний из базы
     * @param textToSearch текст поиска
     * @return список сущностей компаний подходящих по параметрам поиска
     */
    List<Company> search(String textToSearch);

    /**
     * Удаление сущности компании из базы
     * @param id ID сущности компании
     * @return true - если операция выполнена успешно;
     *         false - если операция не выполнена
     */
    boolean delete(int id);
}
