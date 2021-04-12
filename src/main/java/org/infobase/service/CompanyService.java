package org.infobase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import org.infobase.model.Company;
import org.infobase.repository.CompanyRepository;

@Service
public class CompanyService {
    private static final Logger log = LoggerFactory.getLogger(CompanyService.class);

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public int saveOrUpdate(Company company) {
        if (company.getId() == null) {
            log.info("Save {}", company);
            return companyRepository.save(company);
        }
        log.info("Update {}", company);
        return companyRepository.update(company);
    }

    public Company get(int id) {
        log.info("Get company with id:{}", id);
        return companyRepository.getById(id);
    }

    public List<Company> getAll() {
        log.debug("Get all companies");
        return companyRepository.getAll();
    }

    public void delete(int id) {
        log.info("Delete company with id:{}", id);
        companyRepository.delete(id);
    }
}
