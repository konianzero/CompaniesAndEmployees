package org.infobase.web.view;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.Objects;
import java.util.Optional;

import org.infobase.web.component.grid.EntityGrid;

@Route
public class MainView extends VerticalLayout {

    private final TabsPanel tabsPanel;
    private final ButtonsPanel buttonsPanel;
    private final SearchPanel searchPanel;

    public MainView(TabsPanel tabsPanel, ButtonsPanel buttonsPanel, SearchPanel searchPanel) {
        this.tabsPanel = tabsPanel;
        this.buttonsPanel = buttonsPanel;
        this.searchPanel = searchPanel;

        init();
    }

    private void init() {
        setPreSelectedTab();

        // add tab change listener
        tabsPanel.tabs.addSelectedChangeListener(event -> {
            tabsPanel.tabComponents.values().forEach(grid -> grid.getComponent().setVisible(false));

            EntityGrid grid = tabsPanel.tabComponents.get(tabsPanel.tabs.getSelectedTab());
            searchPanel.searchColumn.setItems(grid.getHeaders());
            searchPanel.companyPicker.setCompaniesNamesAsItems(tabsPanel.employeeGrid.getCompaniesNames());
            grid.disableEditButtons();
            grid.fill();
            grid.getComponent().setVisible(true);
        });

        // Enable edit and del buttons when grid item selected, otherwise disabled
        tabsPanel.companyGrid.setEnableEditButtons(buttonsPanel::setEditAndDelEnabled);
        tabsPanel.employeeGrid.setEnableEditButtons(buttonsPanel::setEditAndDelEnabled);

        //  add click listeners
        buttonsPanel.addBtn.addClickListener(e -> tabsPanel.tabComponents.get(tabsPanel.tabs.getSelectedTab()).onCreate());
        buttonsPanel.editBtn.addClickListener(e -> tabsPanel.tabComponents.get(tabsPanel.tabs.getSelectedTab()).onEdit());
        buttonsPanel.delBtn.addClickListener(e -> tabsPanel.tabComponents.get(tabsPanel.tabs.getSelectedTab()).onDelete());

        // search actions
        searchPanel.searchColumn.addValueChangeListener(e -> {
            if (Objects.isNull(e.getValue())) {
                tabsPanel.tabComponents.get(tabsPanel.tabs.getSelectedTab()).fill();
                searchPanel.searchText.setValue("");
                searchPanel.setSearchTextVisibleAndEnable(true);
            }
            else if (e.getValue().equals("Дата Рождения")) {
                searchPanel.setDatePickerVisibleAndEnable(true);
            }
            else if (e.getValue().equals("Компания")) {
                searchPanel.setCompanyPickerVisibleAndEnable(true);
            }
            else {
                searchPanel.setSearchTextVisibleAndEnable(true);
            }
        });

        searchPanel.searchText.addKeyPressListener(e -> {
            if (Objects.nonNull(searchPanel.searchColumn.getValue())) {
                tabsPanel.tabComponents.get(tabsPanel.tabs.getSelectedTab())
                                       .onSearch(searchPanel.searchColumn.getValue(), searchPanel.searchText.getValue());
            }
        });

        searchPanel.birthDatePicker.addValueChangeListener(e -> {
            if (Objects.nonNull(searchPanel.searchColumn.getValue())) {
                tabsPanel.tabComponents.get(tabsPanel.tabs.getSelectedTab())
                                       .onSearch(searchPanel.searchColumn.getValue(), e.getValue().toString());
            }
        });

        searchPanel.companyPicker.addValueChangeListener(e -> {
            if (Objects.nonNull(searchPanel.searchColumn.getValue())) {
                tabsPanel.tabComponents.get(tabsPanel.tabs.getSelectedTab())
                                       .onSearch(searchPanel.searchColumn.getValue(), e.getValue());
            }
        });

        // add to layout
        HorizontalLayout actions = new HorizontalLayout();
        actions.add(buttonsPanel.buttons, searchPanel.filter);
        add(actions, tabsPanel.tabs, tabsPanel.pages);
    }

    private void setPreSelectedTab() {
        tabsPanel.tabs.setSelectedTab(tabsPanel.companyTab);
        searchPanel.searchColumn.setItems(tabsPanel.companyGrid.getHeaders());
        tabsPanel.companyGrid.fill();
        tabsPanel.employeeGrid.setVisible(false);
    }
}
