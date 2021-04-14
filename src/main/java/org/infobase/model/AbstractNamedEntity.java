package org.infobase.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Абстрактный класс сущности с именем
 */
@NoArgsConstructor
@Getter
@Setter
public class AbstractNamedEntity extends AbstractBaseEntity {

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
     * @see AbstractBaseEntity#AbstractBaseEntity(Integer)
     */
    protected AbstractNamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }
}
