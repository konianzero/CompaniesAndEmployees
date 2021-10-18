package org.infobase.model;

import lombok.*;

/**
 * Базовой класс сущности с полем ID
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@EqualsAndHashCode
public class BaseEntity implements HasId {
    /** Начальное значение для последовательности ID, используется для тестов */
    public static final int START_SEQ = 1000;
    /** Поле ID */
    protected Integer id;
}
