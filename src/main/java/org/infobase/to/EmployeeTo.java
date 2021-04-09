package org.infobase.to;

import java.time.LocalDate;

public class EmployeeTo {
    private Integer id;
    private String name;
    private LocalDate birthDate;
    private String email;
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
}
