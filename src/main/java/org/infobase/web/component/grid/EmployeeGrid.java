package org.infobase.web.component.grid;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import org.infobase.service.CompanyService;
import org.infobase.to.EmployeeTo;
import org.infobase.service.EmployeeService;
import org.infobase.web.component.notification.OperationNotification;
import org.infobase.web.component.dialog.EmployeeDialog;

import java.util.Collection;
import java.util.List;

/**
 * Таблица сотрудников
 */
@SpringComponent
@UIScope
public class EmployeeGrid extends EntityGrid<EmployeeTo> implements OperationNotification {
    /** Имя таблицы */
    private static final String NAME = "Сотрудники";

    /** Класс бизнес логики для сущностей компаний */
    private final CompanyService companyService;
    /** Класс бизнес логики для сущностей сотрудников */
    private final EmployeeService employeeService;
    /** Диалоговое окно для редактирования данных сотрудников */
    private final EmployeeDialog employeeDialog;

    public EmployeeGrid(CompanyService companyService, EmployeeService employeeService, EmployeeDialog employeeDialog) {
        this.companyService = companyService;
        this.employeeService = employeeService;
        this.employeeDialog = employeeDialog;

        this.employeeDialog.setOnSave(this::fill);

        setHeight("300px");
        addColumn(EmployeeTo::getId).setHeader(EmployeeHeaders.ID.getHeader());
        addColumn(EmployeeTo::getName).setHeader(EmployeeHeaders.FULL_NAME.getHeader());
        addColumn(EmployeeTo::getBirthDate).setHeader(EmployeeHeaders.BIRTH_DATE.getHeader());
        addColumn(EmployeeTo::getEmail).setHeader(EmployeeHeaders.EMAIL.getHeader());
        addColumn(EmployeeTo::getCompanyName).setHeader(EmployeeHeaders.COMPANY.getHeader());

        setSelectionListener();
    }

    /**
     * Получить имена компаний
     * @return Список имен компаний
     */
    public List<String> getCompaniesNames() {
        return companyService.getNames();
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> getHeaders() {
        return EmployeeHeaders.getGridHeaders();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fill() {
        setItems(employeeService.getAll());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate() {
        if (!isVisible()) { return; }

        processingEmployee(new EmployeeTo());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEdit() {
        if (!isVisible()) { return; }

        processingEmployee(getSelectedEmployee());
    }

    /**
     * Обработка данных сотрудника
     * @param employeeTo Сущность сотрудника для отображения в базе
     */
    private void processingEmployee(EmployeeTo employeeTo) {
        employeeDialog.setItemsToCompanyComboBox(getCompaniesNames());
        employeeDialog.edit(employeeTo);
        employeeDialog.open();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSearch(String columnHeader, String textToSearch) {
        setItems(employeeService.search(EmployeeHeaders.getHeaderFrom(columnHeader), textToSearch));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDelete() {
        if (!isVisible()) { return; }

        preRemoveNotification(this::deleteEmployee, "Сотрудник будет удалён.");
    }

    /**
     * Удаление сотрудника
     */
    private void deleteEmployee() {
        if (employeeService.delete(getSelectedEmployee().getId())) {
            afterOperationNotification("Сотрудник удалён!");
            fill();
        } else {
            afterOperationNotification("Произошла ошибка при удалении!");
        }
    }

    /**
     * Получить сущность выбранного в таблице сотрудника
     * @return сущность выбранного сотрудника
     */
    private EmployeeTo getSelectedEmployee() { return getSelectedItems().stream().findFirst().orElseThrow(); }
}
