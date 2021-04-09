package org.infobase.web.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.infobase.service.EmployeeService;
import org.infobase.to.EmployeeTo;
import org.springframework.beans.factory.annotation.Autowired;

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
        addColumn(EmployeeTo::getEmail).setHeader("Электронная поста");
        addColumn(EmployeeTo::getCompanyName).setHeader("Компания");
    }

    @Override
    public Component getComponent() {
        fill();
        return this;
    }

    public void fill() {
        setItems(employeeService.getAll());
    }
}
