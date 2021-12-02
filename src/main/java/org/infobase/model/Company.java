package org.infobase.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Класс описывающий компанию, используется для отображения данных из базы и в интерфейс
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Company extends NamedEntity {
    /** ИНН */
    @NotBlank
    @Size(min = 10, max = 10)
    private String tin;

    /** Адрес */
    @NotBlank
    private String address;

    /** Телефонный номер */
    @NotBlank
    @Size(min = 7, max = 12)
    private String phoneNumber;

    /**
     * Создание новой компании с ID, названием, ИНН, адресом и телефонным номером
     * @param id уникальный идентификатор компании
     * @param name название компании
     * @param tin ИНН
     * @param address адрес
     * @param phoneNumber телефонный номер
     */
    public Company(Integer id, String name, String tin, String address, String phoneNumber) {
        super(id, name);
        this.tin = tin;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
