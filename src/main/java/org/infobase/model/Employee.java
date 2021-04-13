package org.infobase.model;

import java.time.LocalDate;

public class Employee extends AbstractNamedEntity {

    private LocalDate birthDate;
    private String email;
    private Company company;

    public Employee() {
    }

    public Employee(Integer id, String name, LocalDate birthDate, String email, Company company) {
        super(id, name);
        this.birthDate = birthDate;
        this.email = email;
        this.company = company;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + birthDate +
                ", email='" + email + '\'' +
                ", company=" + company +
                '}';
    }
}
