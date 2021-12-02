package org.infobase.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Абстрактный класс сущности с именем
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class NamedEntity extends BaseEntity {

    /** Имя сущности */
    @NotBlank
    @Size(min = 2, max = 100)
    protected String name;

    /**
     * Конструктор с указанием ID и именем
     * @param id уникальный идентификатор
     * @param name имя сущности
     * @see Employee#Employee(Integer, String, LocalDate, String, Company)
     * @see Company#Company(Integer, String, String, String, String)
     * @see BaseEntity#BaseEntity(Integer)
     */
    protected NamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }
}
