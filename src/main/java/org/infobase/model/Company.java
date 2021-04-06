package org.infobase.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "companies", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}, name = "companies_unique_idx"))
public class Company extends AbstractNamedEntity {
    @NotNull
    @Column(name = "tin", nullable = false)
    private long tin;

    @NotBlank
    @Column(name = "address", nullable = false)
    @Size(min = 30, max = 5000)
    private String address;

    @NotBlank
    @Column(name = "phone_number", nullable = false)
    @Size(min = 7, max = 12)
    private String phoneNumber;

    public Company() {
    }

    public Company(Integer id, String name, long tin, String address, String phoneNumber) {
        super(id, name);
        this.tin = tin;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public long getTin() {
        return tin;
    }

    public void setTin(int tin) {
        this.tin = tin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tin=" + tin +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
