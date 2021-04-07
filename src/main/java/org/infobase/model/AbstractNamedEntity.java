package org.infobase.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AbstractNamedEntity extends AbstractBaseEntity {

    @NotBlank
    @Size(min = 2)
    protected String name;

    protected AbstractNamedEntity() {
    }

    protected AbstractNamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
