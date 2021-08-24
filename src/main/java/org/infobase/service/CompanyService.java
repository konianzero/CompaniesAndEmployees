package org.infobase.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.infobase.model.Company;
import org.infobase.dao.impl.CompanyDaoImpl;

/**
 * Класс бизнес логики для сущностей компаний
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyService {

    /** Класс доступа к данным компаний */
    private final CompanyDaoImpl companyDaoImpl;

    /**
     * Сохранение сущности новой компании или обновление существующей
     * @param company сущность компании
     * @return 0 - если операция не выполнена;
     *         число больше нуля если операция прошла успешно
     */
    public int saveOrUpdate(Company company) {
        if (company.isNew()) {
            log.info("Save {}", company);
            return companyDaoImpl.save(company);
        }
        log.info("Update {}", company);
        return companyDaoImpl.update(company);
    }

    /**
     * Получить сущность компании по ID
     * @param id ID сущности компании
     * @return сущность компании
     */
    public Company get(int id) {
        log.info("Get company with id:{}", id);
        return companyDaoImpl.getById(id);
    }

    /**
     * Получение списка сущностей всех компаний
     * @return список сущностей все компаний
     */
    public List<Company> getAll() {
        log.info("Get all companies");
        return companyDaoImpl.getAll();
    }

    /**
     * Получить имена всех компаний
     * @return список имен всех компаний
     */
    public List<String> getNames() {
        log.info("Get all companies names");
        return companyDaoImpl.getAll()
                             .stream()
                             .map(Company::getName)
                             .collect(Collectors.toList());
    }

    /**
     * Поиск сущностей компаний
     * @param textToSearch текст поиска
     * @return список сущностей компаний подходящих по параметрам поиска
     */
    public List<Company> search(String textToSearch) {
        log.info("Search companies with \"{}\"", textToSearch);
        return companyDaoImpl.search(textToSearch);
    }

    /**
     * Удаление сущности компании
     * @param id ID сущности компании
     * @return true - если операция выполнена успешно;
     *         false - если операция не выполнена
     */
    public boolean delete(int id) {
        log.info("Delete company with id:{}", id);
        return companyDaoImpl.delete(id);
    }
}
