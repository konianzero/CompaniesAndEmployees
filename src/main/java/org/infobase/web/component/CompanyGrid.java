package org.infobase.web.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.infobase.model.Company;
import org.infobase.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class CompanyGrid extends Grid<Company> implements EntityGrid {

    private final CompanyService companyService;

    @Autowired
    public CompanyGrid(CompanyService companyService) {
        this.companyService = companyService;

        setHeight("300px");
        addColumn(Company::getId).setHeader("ID");
        addColumn(Company::getName).setHeader("Название");
        addColumn(Company::getTin).setHeader("ИНН");
        addColumn(Company::getAddress).setHeader("Адрес");
        addColumn(Company::getPhoneNumber).setHeader("Телефонный номер");
    }

    @Override
    public Component getComponent() {
        fill();
        return this;
    }

    public void fill() {
        setItems(companyService.getAll());
    }
}
