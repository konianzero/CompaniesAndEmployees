package org.infobase.web.view;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.Objects;

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
            grid.fill();
            grid.getComponent().setVisible(true);
        });

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
                searchPanel.setDatePickerVisibleAndEnable(false);
            }
            else if (e.getValue().equals("Дата Рождения")) {
                searchPanel.setSearchTextVisibleAndEnable(false);
                searchPanel.setDatePickerVisibleAndEnable(true);
            }
            else {
                searchPanel.setSearchTextVisibleAndEnable(true);
                searchPanel.setDatePickerVisibleAndEnable(false);
            }
        });

        searchPanel.searchText.addKeyPressListener(e -> {
            if (Objects.nonNull(searchPanel.searchColumn.getValue())) {
                tabsPanel.tabComponents.get(tabsPanel.tabs.getSelectedTab()).onSearch(searchPanel.searchColumn.getValue(), searchPanel.searchText.getValue());
            }
        });

        searchPanel.birthDatePicker.addValueChangeListener(e -> {
            if (Objects.nonNull(searchPanel.searchColumn.getValue())) {
                tabsPanel.tabComponents.get(tabsPanel.tabs.getSelectedTab()).onSearch(searchPanel.searchColumn.getValue(), e.getValue().toString());
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
