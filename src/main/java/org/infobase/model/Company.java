package org.infobase.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Company extends AbstractNamedEntity {
    @NotBlank
    @Size(min = 10, max = 10)
    private String tin;

    @NotBlank
    private String address;

    @NotBlank
    @Size(min = 7, max = 12)
    private String phoneNumber;

    public Company() {
    }

    public Company(Integer id, String name, String tin, String address, String phoneNumber) {
        super(id, name);
        this.tin = tin;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
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
