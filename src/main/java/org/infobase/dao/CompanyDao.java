package org.infobase.dao;

import java.util.List;

import org.infobase.model.Company;

public interface CompanyDao {
    int save(Company company);
    int update(Company company);
    Company getById(int id);
    Company getByName(String name);
    List<Company> getAll();
    List<Company> search(String textToSearch);
    boolean delete(int id);
}
