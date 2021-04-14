package org.infobase.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Company extends AbstractNamedEntity {
    @NotBlank
    @Size(min = 10, max = 10)
    private String tin;

    @NotBlank
    private String address;

    @NotBlank
    @Size(min = 7, max = 12)
    private String phoneNumber;

    public Company(Integer id, String name, String tin, String address, String phoneNumber) {
        super(id, name);
        this.tin = tin;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
