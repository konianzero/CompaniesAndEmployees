package org.infobase.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AbstractBaseEntity {
    public static final int START_SEQ = 1000;
    protected Integer id;

    protected AbstractBaseEntity(Integer id) {
        this.id = id;
    }
}
