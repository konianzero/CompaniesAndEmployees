package org.infobase.model;

import lombok.*;

import java.time.LocalDate;

/**
 * Класс описывающий сотрудника, используется для отображения данных из базы
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Employee extends NamedEntity {
    /** Дата рождения */
    private LocalDate birthDate;
    /** Электронная почта */
    private String email;
    /** Компания в которой работает сотрудник */
    private Company company;

    /**
     * Создание нового сотрудника с ID, именем, датой рождения, электронной почтой и компанией
     * @param id  уникальный идентификатор сотрудника
     * @param name имя сотрудника
     * @param birthDate дата рождения сотрудника
     * @param email электронная почта сотрудника
     * @param company компания
     */
    public Employee(Integer id, String name, LocalDate birthDate, String email, Company company) {
        super(id, name);
        this.birthDate = birthDate;
        this.email = email;
        this.company = company;
    }
}
