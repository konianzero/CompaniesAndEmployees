package org.infobase.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Базовой класс сущности с полем ID
 */
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AbstractBaseEntity implements HasId {
    /** Начальное значение для последовательности ID, используется для тестов */
    public static final int START_SEQ = 1000;
    /** Поле ID */
    protected Integer id;

    /**
     * Конструктор с указанием ID
     * @param id уникальный идентификатор
     * @see AbstractNamedEntity#AbstractNamedEntity(Integer, String)
     */
    protected AbstractBaseEntity(Integer id) {
        this.id = id;
    }
}
