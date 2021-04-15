package org.infobase.web.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

import org.infobase.web.component.grid.CompanyGrid;
import org.infobase.web.component.grid.EmployeeGrid;
import org.infobase.web.component.grid.EntityGrid;

@SpringComponent
@UIScope
@Getter
public class TabsPanel {

    private final CompanyGrid companyGrid;
    private final EmployeeGrid employeeGrid;

    private final Tab companyTab;
    private final Tab employeeTab;
    private final Tabs tabs;
    private final Map<Tab, EntityGrid> tabComponents;
    private final Div pages;

    public TabsPanel(CompanyGrid companyGrid, EmployeeGrid employeeGrid) {
        this.companyGrid = companyGrid;
        this.employeeGrid = employeeGrid;

        companyTab = new Tab(this.companyGrid.getName());
        employeeTab = new Tab(this.employeeGrid.getName());
        tabs = new Tabs(companyTab, employeeTab);

        tabComponents = new HashMap<>();
        tabComponents.put(companyTab, this.companyGrid);
        tabComponents.put(employeeTab, this.employeeGrid);

        pages = new Div(this.companyGrid, this.employeeGrid);
        pages.setSizeFull();
    }

    public EntityGrid getSelectedGrid() {
        return tabComponents.get(tabs.getSelectedTab());
    }

    public void setPreSelectedTab() {
        tabs.setSelectedTab(companyTab);
        companyGrid.fill();
        employeeGrid.setVisible(false);
    }
}
