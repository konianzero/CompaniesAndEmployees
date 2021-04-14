package org.infobase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.infobase.model.Company;
import org.infobase.dao.impl.CompanyDaoImpl;

@Service
public class CompanyService {
    private static final Logger log = LoggerFactory.getLogger(CompanyService.class);

    private final CompanyDaoImpl companyDaoImpl;

    public CompanyService(CompanyDaoImpl companyDaoImpl) {
        this.companyDaoImpl = companyDaoImpl;
    }

    public int saveOrUpdate(Company company) {
        if (company.getId() == null) {
            log.info("Save {}", company);
            return companyDaoImpl.save(company);
        }
        log.info("Update {}", company);
        return companyDaoImpl.update(company);
    }

    public Company get(int id) {
        log.info("Get company with id:{}", id);
        return companyDaoImpl.getById(id);
    }

    public List<Company> getAll() {
        log.debug("Get all companies");
        return companyDaoImpl.getAll();
    }

    public List<String> getNames() {
        log.debug("Get all companies");
        return companyDaoImpl.getAll()
                             .stream()
                             .map(Company::getName)
                             .collect(Collectors.toList());
    }

    public List<Company> search(String textToSearch) {
        log.info("Search companies with \"{}\"", textToSearch);
        return companyDaoImpl.search(textToSearch);
    }

    public void delete(int id) {
        log.info("Delete company with id:{}", id);
        companyDaoImpl.delete(id);
    }
}
