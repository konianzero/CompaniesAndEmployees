package org.infobase.web.component.grid;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import org.infobase.model.Company;
import org.infobase.service.CompanyService;
import org.infobase.web.component.dialog.CompanyDialog;

import java.util.*;

@SpringComponent
@UIScope
public class CompanyGrid extends Grid<Company> implements EntityGrid {

    private final CompanyService companyService;
    private final CompanyDialog companyDialog;
    
    private final Map<String, String> headersMap = Map.of(
            "Название", "name",
            "ИНН", "tin",
            "Адрес", "address",
            "Телефон", "phone_number");

    @Autowired
    public CompanyGrid(CompanyService companyService, CompanyDialog companyDialog) {
        this.companyService = companyService;
        this.companyDialog = companyDialog;

        this.companyDialog.setOnSave(this::fill);

        setHeight("300px");
        addColumn(Company::getId).setHeader("ID");
        addColumn(Company::getName).setHeader("Название");
        addColumn(Company::getTin).setHeader("ИНН");
        addColumn(Company::getAddress).setHeader("Адрес");
        addColumn(Company::getPhoneNumber).setHeader("Телефон");
    }

    @Override
    public Component getComponent() {
        return this;
    }

    public Collection<String> getHeaders() {
        return headersMap.keySet();
    }

    @Override
    public void fill() {
        setItems(companyService.getAll());
    }

    private Company getSelectedCompany() {
        return getSelectedItems().stream().findFirst().orElseThrow();
    }

    @Override
    public void onCreate() {
        if (!isVisible()) { return; }

        companyDialog.edit(new Company());
        companyDialog.open();
    }

    @Override
    public void onEdit() {
        if (!isVisible()) { return; }

        companyDialog.edit(getSelectedCompany());
        companyDialog.open();
    }

    @Override
    public void onSearch(String columnHeader, String textToSearch) {
        setItems(companyService.search(headersMap.get(columnHeader), textToSearch));
    }

    @Override
    public void onDelete() {
        if (!isVisible()) { return; }

        preRemoveNotification(this::deleteCompany);
    }

    private void deleteCompany() {
        companyService.delete(getSelectedCompany().getId());
        fill();
    }
}
