package org.infobase.web.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.util.HashMap;
import java.util.Map;

import org.infobase.web.component.grid.CompanyGrid;
import org.infobase.web.component.grid.EmployeeGrid;
import org.infobase.web.component.grid.EntityGrid;

@SpringComponent
@UIScope
public class TabsPanel {

    final CompanyGrid companyGrid;
    final EmployeeGrid employeeGrid;

    final Tab companyTab;
    final Tab employeeTab;
    final Tabs tabs;
    final Map<Tab, EntityGrid> tabComponents;
    final Div pages;

    public TabsPanel(CompanyGrid companyGrid, EmployeeGrid employeeGrid) {
        this.companyGrid = companyGrid;
        this.employeeGrid = employeeGrid;

        companyTab = new Tab("Компании");
        employeeTab = new Tab("Сотрудники");
        tabs = new Tabs(companyTab, employeeTab);

        tabComponents = new HashMap<>();
        tabComponents.put(companyTab, this.companyGrid);
        tabComponents.put(employeeTab, this.employeeGrid);

        pages = new Div(this.companyGrid, this.employeeGrid);
        pages.setSizeFull();
    }
}
