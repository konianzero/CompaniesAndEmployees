package org.infobase.model;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Employee extends AbstractNamedEntity {
    private LocalDate birthDate;
    private String email;
    private Company company;

    public Employee(Integer id, String name, LocalDate birthDate, String email, Company company) {
        super(id, name);
        this.birthDate = birthDate;
        this.email = email;
        this.company = company;
    }
}
