package org.infobase.web.component.grid;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import org.infobase.model.Company;
import org.infobase.service.CompanyService;
import org.infobase.util.formatter.PhoneFormatter;
import org.infobase.web.component.notification.OperationNotification;
import org.infobase.web.component.dialog.CompanyDialog;

import java.util.*;

/**
 * Таблица компаний
 */
@SpringComponent
@UIScope
public class CompanyGrid extends EntityGrid<Company> implements OperationNotification {
    /** Имя таблицы */
    private static final String NAME = "Компании";

    /** Класс бизнес логики для сущностей компаний */
    private final CompanyService companyService;
    /** Диалоговое окно для редактирования данных компаний */
    private final CompanyDialog companyDialog;

    /** Список заголовков таблицы */
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
        addColumn(PhoneFormatter.formatPhoneNumberProvider()).setHeader(headersList.get(4));

        setSelectionListener();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Component getComponent() {
        return this;
    }

    public Collection<String> getHeaders() {
        return headersList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fill() {
        setItems(companyService.getAll());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate() {
        if (!isVisible()) { return; }

        companyDialog.edit(new Company());
        companyDialog.open();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEdit() {
        if (!isVisible()) { return; }

        companyDialog.edit(getSelectedCompany());
        companyDialog.open();
    }

    /**
     * Получить сущность выбранной в таблице компании
     * @return сущность выбранной компании
     */
    private Company getSelectedCompany() {
        return getSelectedItems().stream().findFirst().orElseThrow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSearch(String columnHeader, String textToSearch) {
        setItems(companyService.search(textToSearch));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDelete() {
        if (!isVisible()) { return; }

        preRemoveNotification(this::deleteCompany, "Будет удалена компания и все её сотрудники.");
    }

    /**
     * Удаление компании
     */
    private void deleteCompany() {
        if (companyService.delete(getSelectedCompany().getId())) {
            afterOperationNotification("Компания и все её сотрудники удалены!");
            fill();
        } else {
            afterOperationNotification("Произошла ошибка при удалении!");
        }
    }
}
