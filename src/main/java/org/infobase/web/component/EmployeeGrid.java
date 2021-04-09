package org.infobase.web.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.infobase.model.Employee;
import org.infobase.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class EmployeeGrid extends Grid<Employee> implements EntityGrid {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeGrid(EmployeeService employeeService) {
        this.employeeService = employeeService;

        setHeight("300px");
        addColumn(Employee::getId).setHeader("ID");
        addColumn(Employee::getName).setHeader("ФИО");
        addColumn(Employee::getBirthDate).setHeader("Дата Рождения");
        addColumn(Employee::getEmail).setHeader("Электронная поста");
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
