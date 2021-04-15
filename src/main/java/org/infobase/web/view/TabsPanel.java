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

/**
 * Панель вкладок, содержит вкладки:
 * <br>- Компании
 * <br>- Сотрудники
 */
@SpringComponent
@UIScope
@Getter
public class TabsPanel {
    /** Таблица компаний */
    private final CompanyGrid companyGrid;
    /** Таблица сотрудников */
    private final EmployeeGrid employeeGrid;

    /** Вкладка компаний */
    private final Tab companyTab;
    /** Вкладка сотрудников */
    private final Tab employeeTab;
    /** Все вкладки */
    private final Tabs tabs;
    /** Словарь Вкладки - Таблицы */
    private final Map<Tab, EntityGrid> tabComponents;
    /** Все таблицы */
    private final Div pages;

    /**
     * Инициализация панели вкладок
     * @param companyGrid таблица компаний
     * @param employeeGrid таблица сотрудников
     */
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

    /**
     * Получить выбранную таблицу
     * @return EntityGrid - общая сущность таблиц
     */
    public EntityGrid getSelectedGrid() {
        return tabComponents.get(tabs.getSelectedTab());
    }

    /**
     * Задать вкладку компаний по умолчанию
     */
    public void setPreSelectedTab() {
        tabs.setSelectedTab(companyTab);
        companyGrid.fill();
        employeeGrid.setVisible(false);
    }
}
