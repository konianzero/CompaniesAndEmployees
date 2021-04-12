package org.infobase.web.component.grid;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import org.infobase.to.EmployeeTo;
import org.infobase.service.EmployeeService;
import org.infobase.web.component.dialog.EmployeeDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@SpringComponent
@UIScope
public class EmployeeGrid extends Grid<EmployeeTo> implements EntityGrid {

    private final EmployeeService employeeService;
    private final EmployeeDialog employeeDialog;

    private final Map<String, String> headersMap = Map.of(
            "ФИО", "name",
            "Дата Рождения", "birth_date",
            "Электронная почта", "email",
            "Компания", "comp_name");

    @Autowired
    public EmployeeGrid(EmployeeService employeeService, EmployeeDialog employeeDialog) {
        this.employeeService = employeeService;
        this.employeeDialog = employeeDialog;

        this.employeeDialog.setOnSave(this::fill);

        setHeight("300px");
        addColumn(EmployeeTo::getId).setHeader("ID");
        addColumn(EmployeeTo::getName).setHeader("ФИО");
        addColumn(EmployeeTo::getBirthDate).setHeader("Дата Рождения");
        addColumn(EmployeeTo::getEmail).setHeader("Электронная почта");
        addColumn(EmployeeTo::getCompanyName).setHeader("Компания");
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public Collection<String> getHeaders() {
        return headersMap.keySet();
    }

    @Override
    public void fill() {
        setItems(employeeService.getAll());
    }

    private EmployeeTo getSelectedEmployee() { return getSelectedItems().stream().findFirst().orElseThrow(); }

    @Override
    public void onCreate() {
        if (!isVisible()) { return; }

        employeeDialog.edit(new EmployeeTo());
        employeeDialog.open();
    }

    @Override
    public void onEdit() {
        if (!isVisible()) { return; }

        employeeDialog.edit(getSelectedEmployee());
        employeeDialog.open();
    }

    @Override
    public void onSearch(String columnHeader, String textToSearch) {
        setItems(employeeService.search(headersMap.get(columnHeader), textToSearch));
    }

    @Override
    public void onDelete() {
        if (!isVisible()) { return; }

        preRemoveNotification(this::deleteEmployee);
    }

    private void deleteEmployee() {
        employeeService.delete(getSelectedEmployee().getId());
        fill();
    }
}
