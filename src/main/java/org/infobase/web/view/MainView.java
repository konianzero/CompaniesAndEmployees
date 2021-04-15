package org.infobase.web.view;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
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

        // Add tab change listener
        tabsPanel.tabs.addSelectedChangeListener(event -> {
            tabsPanel.tabComponents.values().forEach(grid -> grid.getComponent().setVisible(false));

            EntityGrid grid = tabsPanel.tabComponents.get(tabsPanel.tabs.getSelectedTab());

            if (grid.getName().equals("Компании")) {
                searchPanel.showSearchColumn(false);
            } else {
                searchPanel.showSearchColumn(true);
                searchPanel.disabledSearchInput();
                searchPanel.getColumnToSearch().setItems(grid.getHeaders());
                searchPanel.getCompanyPicker().setCompaniesNamesAsItems(tabsPanel.employeeGrid.getCompaniesNames());
            }

            grid.fill();
            grid.disableEditButtons();
            grid.getComponent().setVisible(true);
        });

        // Enable edit and del buttons when grid item selected, otherwise disabled
        tabsPanel.companyGrid.setEnableEditButtons(buttonsPanel::setEditAndDelEnabled);
        tabsPanel.employeeGrid.setEnableEditButtons(buttonsPanel::setEditAndDelEnabled);

        // Add click listeners
        buttonsPanel.getAddBtn().addClickListener(e -> tabsPanel.tabComponents.get(tabsPanel.tabs.getSelectedTab()).onCreate());
        buttonsPanel.getEditBtn().addClickListener(e -> tabsPanel.tabComponents.get(tabsPanel.tabs.getSelectedTab()).onEdit());
        buttonsPanel.getDelBtn().addClickListener(e -> tabsPanel.tabComponents.get(tabsPanel.tabs.getSelectedTab()).onDelete());

        // Search actions
        searchPanel.getColumnToSearch().addValueChangeListener(e -> {
            if (Objects.nonNull(e.getValue())) {

                if (e.getValue().equals("Дата Рождения")) {
                    searchPanel.showDatePicker();
                } else if (e.getValue().equals("Компания")) {
                    searchPanel.showCompanyPicker();
                } else {
                    searchPanel.showSearchText();
                }
            } else {
                searchPanel.disabledSearchInput();
                searchPanel.clearSearchInput();
                tabsPanel.tabComponents.get(tabsPanel.tabs.getSelectedTab()).fill();
            }
        });

        searchPanel.getTextToSearch().addKeyPressListener(Key.ENTER, e -> {
            if (searchPanel.getColumnToSearch().isEnabled() && Objects.nonNull(searchPanel.getColumnToSearch().getValue())) {
                tabsPanel.tabComponents.get(tabsPanel.tabs.getSelectedTab())
                                       .onSearch(searchPanel.getColumnToSearch().getValue(), searchPanel.getTextToSearch().getValue());
            } else {
                tabsPanel.tabComponents.get(tabsPanel.tabs.getSelectedTab())
                        .onSearch("", searchPanel.getTextToSearch().getValue());
            }
        });

        searchPanel.getBirthDatePicker().addValueChangeListener(e -> {
            if (Objects.nonNull(searchPanel.getColumnToSearch().getValue())) {
                tabsPanel.tabComponents.get(tabsPanel.tabs.getSelectedTab())
                                       .onSearch(
                                               searchPanel.getColumnToSearch().getValue(),
                                               Optional.ofNullable(e.getValue()).map(LocalDate::toString).orElse("")
                                       );
            }
        });

        searchPanel.getCompanyPicker().addValueChangeListener(e -> {
            if (Objects.nonNull(searchPanel.getColumnToSearch().getValue())) {
                tabsPanel.tabComponents.get(tabsPanel.tabs.getSelectedTab())
                                       .onSearch(
                                               searchPanel.getColumnToSearch().getValue(),
                                               Optional.ofNullable(e.getValue()).orElse("")
                                       );
            }
        });

        // Add to layout
        HorizontalLayout actions = new HorizontalLayout();
        actions.add(buttonsPanel.getButtons(), searchPanel.getFilters());
        add(actions, tabsPanel.tabs, tabsPanel.pages);
    }

    private void setPreSelectedTab() {
        tabsPanel.tabs.setSelectedTab(tabsPanel.companyTab);
        searchPanel.showSearchColumn(false);
        tabsPanel.companyGrid.fill();
        tabsPanel.employeeGrid.setVisible(false);
    }
}
