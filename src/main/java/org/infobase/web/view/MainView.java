package org.infobase.web.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import org.infobase.model.Company;
import org.infobase.model.Employee;
import org.infobase.service.CompanyService;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.infobase.service.EmployeeService;

import java.util.HashMap;
import java.util.Map;

@Route
public class MainView extends VerticalLayout {

    private final CompanyService companyService;
    private final EmployeeService employeeService;

    final Grid<Company> companyGrid;
    final Grid<Employee> employeeGrid;

    public MainView(CompanyService companyService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.employeeService = employeeService;

        TextField filter = new TextField("", "Поиск");
        Button addBtn = new Button("Добавить", VaadinIcon.PLUS.create());
        Button editBtn = new Button("Редактировать", VaadinIcon.PENCIL.create());
        Button delBtn = new Button("Удалить", VaadinIcon.MINUS.create());

        HorizontalLayout actions = new HorizontalLayout(addBtn, editBtn, delBtn, filter);

        this.companyGrid = new Grid<>();
        companyGrid.setHeight("300px");
        companyGrid.addColumn(Company::getId).setHeader("ID");
        companyGrid.addColumn(Company::getName).setHeader("Название");
        companyGrid.addColumn(Company::getTin).setHeader("ИНН");
        companyGrid.addColumn(Company::getAddress).setHeader("Адрес");
        companyGrid.addColumn(Company::getPhoneNumber).setHeader("Телефонный номер");

        this.employeeGrid = new Grid<>();
        employeeGrid.setHeight("300px");
        employeeGrid.addColumn(Employee::getId).setHeader("ID");
        employeeGrid.addColumn(Employee::getName).setHeader("ФИО");
        employeeGrid.addColumn(Employee::getBirthDate).setHeader("Дата Рождения");
        employeeGrid.addColumn(Employee::getEmail).setHeader("Электронная поста");

        Tab companyTab = new Tab("Компании");
        Tab employeeTab = new Tab("Сотрудники");

        Div companyPage = new Div(companyGrid);
        Div employeePage = new Div(employeeGrid);

        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(companyTab, companyPage);
        tabsToPages.put(employeeTab, employeePage);

        Tabs tabs = new Tabs(companyTab, employeeTab);
        Div pages = new Div(companyPage, employeePage);

        tabs.addSelectedChangeListener(event -> {
            tabsToPages.values().forEach(page -> page.setVisible(false));
            setItemsToGrid();
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
        });

        add(actions, tabs, pages);
    }

    private void setItemsToGrid() {
        companyGrid.setItems(companyService.getAll());
        employeeGrid.setItems(employeeService.getAll());
    }
}
