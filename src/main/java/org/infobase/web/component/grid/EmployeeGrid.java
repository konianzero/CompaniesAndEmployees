package org.infobase.web.component.grid;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.infobase.model.Employee;
import org.infobase.web.component.dialog.EmployeeDialog;
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
    private final EmployeeDialog employeeDialog;

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
    public void onDelete() {
        if (!isVisible()) { return; }

        preRemoveNotification();
    }

    private void preRemoveNotification() {
        Button yes = new Button("Да");
        Button no = new Button("Нет");
        HorizontalLayout btnLayout = new HorizontalLayout(yes, no);
        btnLayout.setSpacing(true);
        Span text = new Span("Подтвердите удаление сотрудника");

        Notification notification = new Notification(text, btnLayout);
        notification.setPosition(Notification.Position.MIDDLE);
        notification.open();

        yes.addClickListener(event -> {
            deleteEmployee();
            notification.close();
        });
        no.addClickListener(event -> notification.close());
    }

    private void deleteEmployee() {
        employeeService.delete(getSelectedEmployee().getId());
        fill();
    }
}
