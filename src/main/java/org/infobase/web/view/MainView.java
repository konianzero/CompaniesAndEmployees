package org.infobase.web.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
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
import org.infobase.web.component.CompanyGrid;
import org.infobase.web.component.EmployeeGrid;
import org.infobase.web.component.EntityGrid;

import java.util.HashMap;
import java.util.Map;

@Route
public class MainView extends VerticalLayout {

    private final CompanyGrid companyGrid;
    private final EmployeeGrid employeeGrid;

    public MainView(CompanyGrid companyGrid, EmployeeGrid employeeGrid) {
        this.companyGrid = companyGrid;
        this.employeeGrid = employeeGrid;

        init();
    }

    private void init() {
        TextField filter = new TextField("", "Поиск");
        Button addBtn = new Button("Добавить", VaadinIcon.PLUS.create());
        Button editBtn = new Button("Редактировать", VaadinIcon.PENCIL.create());
        Button delBtn = new Button("Удалить", VaadinIcon.MINUS.create());

        HorizontalLayout actions = new HorizontalLayout(addBtn, editBtn, delBtn, filter);

        Tab companyTab = new Tab("Компании");
        Tab employeeTab = new Tab("Сотрудники");
        Tabs tabs = new Tabs(companyTab, employeeTab);
        // Pre-selected tabs
        tabs.setSelectedTab(companyTab);
        companyGrid.fill();
        employeeGrid.setVisible(false);

        Map<Tab, EntityGrid> tabsToPages = new HashMap<>();
        tabsToPages.put(companyTab, companyGrid);
        tabsToPages.put(employeeTab, employeeGrid);

        Div pages = new Div(companyGrid, employeeGrid);
        pages.setSizeFull();

        tabs.addSelectedChangeListener(event -> {
            tabsToPages.values().forEach(grid -> grid.getComponent().setVisible(false));

            EntityGrid grid = tabsToPages.get(tabs.getSelectedTab());
            grid.fill();
            grid.getComponent().setVisible(true);
        });

        add(actions, tabs, pages);
    }
}
