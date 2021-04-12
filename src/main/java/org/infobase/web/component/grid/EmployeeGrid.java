package org.infobase.web.component.grid;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import org.infobase.service.EmployeeService;
import org.infobase.to.EmployeeTo;

@SpringComponent
@UIScope
public class EmployeeGrid extends Grid<EmployeeTo> implements EntityGrid {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeGrid(EmployeeService employeeService) {
        this.employeeService = employeeService;

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
    public void fill() {
        setItems(employeeService.getAll());
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onEdit() {

    }

    @Override
    public void onDelete() {

    }
}
