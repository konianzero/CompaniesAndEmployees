package org.infobase.service;

import org.infobase.model.Company;
import org.infobase.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public int saveOrUpdate(Company company) {
        return companyRepository.saveOrUpdate(company);
    }

    public Company get(int id) {
        return companyRepository.get(id);
    }

    public List<Company> getAll() {
        return companyRepository.getAll();
    }

    public void delete(int id) {
        companyRepository.delete(id);
    }
}
