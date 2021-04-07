package org.infobase.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class Employee extends AbstractNamedEntity {
    @NotNull
    private LocalDate date;

    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    private Company company;

    public Employee() {
    }

    public Employee(Integer id, String name, LocalDate date, String email, Company company) {
        super(id, name);
        this.date = date;
        this.email = email;
        this.company = company;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
                ", date=" + date +
                ", email='" + email + '\'' +
                ", company=" + company +
                '}';
    }
}
