package org.infobase.web.component.grid;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import org.infobase.model.Company;
import org.infobase.service.CompanyService;
import org.infobase.web.component.notification.OperationNotification;
import org.infobase.web.component.dialog.CompanyDialog;

import java.util.*;

@SpringComponent
@UIScope
public class CompanyGrid extends EntityGrid<Company> implements OperationNotification {

    private static final String NAME = "Компании";

    private final CompanyService companyService;
    private final CompanyDialog companyDialog;

    private final List<String> headersList = List.of("ID", "Название", "ИНН", "Адрес", "Телефон");

    public CompanyGrid(CompanyService companyService, CompanyDialog companyDialog) {
        this.companyService = companyService;
        this.companyDialog = companyDialog;

        this.companyDialog.setOnSave(this::fill);

        setHeight("300px");
        addColumn(Company::getId).setHeader(headersList.get(0));
        addColumn(Company::getName).setHeader(headersList.get(1));
        addColumn(Company::getTin).setHeader(headersList.get(2));
        addColumn(Company::getAddress).setHeader(headersList.get(3));
        addColumn(Company::getPhoneNumber).setHeader(headersList.get(4));

        setSelectionListener();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Component getComponent() {
        return this;
    }

    public Collection<String> getHeaders() {
        return headersList;
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
        setItems(companyService.search(textToSearch));
    }

    @Override
    public void onDelete() {
        if (!isVisible()) { return; }

        preRemoveNotification(this::deleteCompany, "Будет удалена компания и все её сотрудники.");
    }

    private void deleteCompany() {
        if (companyService.delete(getSelectedCompany().getId())) {
            afterOperationNotification("Компания и все её сотрудники удалены!");
            fill();
        } else {
            afterOperationNotification("Произошла ошибка при удалении!");
        }
    }
}
