package org.infobase.web;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.infobase.web.component.grid.CompanyGrid;
import org.infobase.web.component.grid.EmployeeGrid;
import org.infobase.web.component.grid.EntityGrid;

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
        // add search
        ComboBox<String> searchColumn = new ComboBox<>();
        searchColumn.setPlaceholder("Выберите поле");
        searchColumn.setRequired(true);
        searchColumn.setClearButtonVisible(true);

        TextField searchText = new TextField("", "Поиск");
        searchText.setEnabled(true);
        searchText.setVisible(true);

        DatePicker birthDatePicker = new DatePicker("");
        birthDatePicker.setEnabled(false);
        birthDatePicker.setVisible(false);

        HorizontalLayout filter = new HorizontalLayout(searchColumn, searchText, birthDatePicker);

        // add buttons
        Button addBtn = new Button("Добавить", VaadinIcon.PLUS.create());
        Button editBtn = new Button("Редактировать", VaadinIcon.PENCIL.create());
        Button delBtn = new Button("Удалить", VaadinIcon.MINUS.create());

        HorizontalLayout actions = new HorizontalLayout(addBtn, editBtn, delBtn, filter);

        // add tabs
        Tab companyTab = new Tab("Компании");
        Tab employeeTab = new Tab("Сотрудники");
        Tabs tabs = new Tabs(companyTab, employeeTab);
        // pre-selected tab
        tabs.setSelectedTab(companyTab);
        searchColumn.setItems(companyGrid.getHeaders());
        companyGrid.fill();
        employeeGrid.setVisible(false);

        Map<Tab, EntityGrid> tabComponents = new HashMap<>();
        tabComponents.put(companyTab, companyGrid);
        tabComponents.put(employeeTab, employeeGrid);

        Div pages = new Div(companyGrid, employeeGrid);
        pages.setSizeFull();

        // add tab change listener
        tabs.addSelectedChangeListener(event -> {
            tabComponents.values().forEach(grid -> grid.getComponent().setVisible(false));

            EntityGrid grid = tabComponents.get(tabs.getSelectedTab());
            searchColumn.setItems(grid.getHeaders());
            grid.fill();
            grid.getComponent().setVisible(true);
        });

        //  add click listeners
        addBtn.addClickListener(e -> tabComponents.get(tabs.getSelectedTab()).onCreate());
        editBtn.addClickListener(e -> tabComponents.get(tabs.getSelectedTab()).onEdit());
        delBtn.addClickListener(e -> tabComponents.get(tabs.getSelectedTab()).onDelete());

        // search actions
        searchColumn.addValueChangeListener(e -> {
            if (Objects.isNull(e.getValue())) {
                tabComponents.get(tabs.getSelectedTab()).fill();
                searchText.setValue("");
                searchText.setEnabled(true);
                searchText.setVisible(true);
                birthDatePicker.setEnabled(false);
                birthDatePicker.setVisible(false);
                return;
            }
            if (e.getValue().equals("Дата Рождения")) {
                searchText.setEnabled(false);
                searchText.setVisible(false);
                birthDatePicker.setEnabled(true);
                birthDatePicker.setVisible(true);
            } else {
                searchText.setEnabled(true);
                searchText.setVisible(true);
                birthDatePicker.setEnabled(false);
                birthDatePicker.setVisible(false);
            }
        });

        searchText.addValueChangeListener(e -> {
            if (Objects.nonNull(searchColumn.getValue())) {
                tabComponents.get(tabs.getSelectedTab()).onSearch(searchColumn.getValue(), e.getValue());
            }
        });

        birthDatePicker.addValueChangeListener(e -> {
            if (Objects.nonNull(searchColumn.getValue())) {
                tabComponents.get(tabs.getSelectedTab()).onSearch(searchColumn.getValue(), e.getValue().toString());
            }
        });

        add(actions, tabs, pages);
    }
}
