package org.infobase.to;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class EmployeeTo {

    private Integer id;

    @NotBlank
    @Size(min = 2)
    private String name;

    @NotNull
    private LocalDate birthDate;

    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    @NotBlank
    private String companyName;

    public EmployeeTo() {
    }

    public EmployeeTo(Integer id, String name, LocalDate birthDate, String email, String companyName) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.companyName = companyName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "EmployeeTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
