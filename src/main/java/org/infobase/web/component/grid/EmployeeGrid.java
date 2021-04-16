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

@SpringComponent
@UIScope
public class EmployeeGrid extends EntityGrid<EmployeeTo> implements OperationNotification {

    private static final String NAME = "Сотрудники";

    private final CompanyService companyService;
    private final EmployeeService employeeService;
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

    public List<String> getCompaniesNames() {
        return companyService.getNames();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public Collection<String> getHeaders() {
        return EmployeeHeaders.getGridHeaders();
    }

    @Override
    public void fill() {
        setItems(employeeService.getAll());
    }

    @Override
    public void onCreate() {
        if (!isVisible()) { return; }

        processingEmployee(new EmployeeTo());
    }

    @Override
    public void onEdit() {
        if (!isVisible()) { return; }

        processingEmployee(getSelectedEmployee());
    }

    private void processingEmployee(EmployeeTo employeeTo) {
        employeeDialog.setItemsToCompanyComboBox(getCompaniesNames());
        employeeDialog.edit(employeeTo);
        employeeDialog.open();
    }

    @Override
    public void onSearch(String columnHeader, String textToSearch) {
        setItems(employeeService.search(EmployeeHeaders.fromGridHeader(columnHeader), textToSearch));
    }

    @Override
    public void onDelete() {
        if (!isVisible()) { return; }

        preRemoveNotification(this::deleteEmployee, "Сотрудник будет удалён.");
    }

    private void deleteEmployee() {
        if (employeeService.delete(getSelectedEmployee().getId())) {
            afterOperationNotification("Сотрудник удалён!");
            fill();
        } else {
            afterOperationNotification("Произошла ошибка при удалении!");
        }
    }

    private EmployeeTo getSelectedEmployee() { return getSelectedItems().stream().findFirst().orElseThrow(); }
}
