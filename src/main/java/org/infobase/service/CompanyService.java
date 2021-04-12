package org.infobase.service;

import org.springframework.stereotype.Service;

import java.util.List;

import org.infobase.model.Company;
import org.infobase.repository.CompanyRepository;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public int saveOrUpdate(Company company) {
        if (company.getId() == null) {
            return companyRepository.save(company);
        }
        return companyRepository.update(company);
    }

    public Company get(int id) {
        return companyRepository.getById(id);
    }

    public List<Company> getAll() {
        return companyRepository.getAll();
    }

    public void delete(int id) {
        companyRepository.delete(id);
    }
}
