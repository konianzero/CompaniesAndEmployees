package org.infobase.web.view;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.Objects;
import java.util.Optional;

import org.infobase.web.component.grid.EntityGrid;

/**
 * Интерфейс, состоит из:
 * <br>- панели кнопок
 * <br>- панели поиска
 * <br>- панели вкладок
 * @see ButtonsPanel
 * @see SearchPanel
 * @see TabsPanel
 */
@Route
public class MainView extends VerticalLayout {
    /** Панель вкладок */
    private final TabsPanel tabsPanel;
    /** Панель кнопок */
    private final ButtonsPanel buttonsPanel;
    /** Панель поиска */
    private final SearchPanel searchPanel;

    /**
     * Создание интерфейса
     * @param tabsPanel панели вкладок
     * @param buttonsPanel панели кнопок
     * @param searchPanel панели поиска
     */
    public MainView(TabsPanel tabsPanel, ButtonsPanel buttonsPanel, SearchPanel searchPanel) {
        this.tabsPanel = tabsPanel;
        this.buttonsPanel = buttonsPanel;
        this.searchPanel = searchPanel;

        init();
    }

    /**
     * Инициализация интерфейса
     */
    private void init() {
        setPreSelectedTab();

        // Действия при изменении вкладок
        tabsPanel.getTabs().addSelectedChangeListener(event -> {
            tabsPanel.getTabComponents().values().forEach(grid -> grid.getComponent().setVisible(false));

            EntityGrid grid = tabsPanel.getSelectedGrid();

            // Для вкладки компаний
            if (grid.getName().equals("Компании")) {
                searchPanel.showSearchColumn(false);
            }
            // Для вкладки сотрудников
            else {
                searchPanel.showSearchColumn(true);
                searchPanel.getColumnToSearch().setItems(grid.getHeaders());
                searchPanel.getCompanyPicker().setItems(tabsPanel.getEmployeeGrid().getCompaniesNames());
            }

            grid.fill();
            grid.disableEditButtons();
            grid.getComponent().setVisible(true);
        });

        // Включить кнопки edit и del при выборе элемента сетки, иначе отключить
        tabsPanel.getCompanyGrid().setEnableEditButtons(buttonsPanel::setEditAndDelEnabled);
        tabsPanel.getEmployeeGrid().setEnableEditButtons(buttonsPanel::setEditAndDelEnabled);

        // Действия для кнопок
        buttonsPanel.getAddBtn().addClickListener(e -> tabsPanel.getSelectedGrid().onCreate());
        buttonsPanel.getEditBtn().addClickListener(e -> tabsPanel.getSelectedGrid().onEdit());
        buttonsPanel.getDelBtn().addClickListener(e -> tabsPanel.getSelectedGrid().onDelete());

        // Поисковые действия
        // Выбор колонки для поиска
        searchPanel.getColumnToSearch().addValueChangeListener(e -> {
            if (Objects.nonNull(e.getValue())) {

                if (e.getValue().equals("Дата Рождения")) {
                    searchPanel.showDatePicker();
                } else if (e.getValue().equals("Компания")) {
                    searchPanel.showCompanyPicker();
                } else {
                    searchPanel.showSearchText();
                }
            } else { // Если не выбрана колонка для поиска, затеняет поля ввода
                searchPanel.disabledSearchInput();
                searchPanel.clearSearchInput();
            }
            // При выборе другой колонки для поиска заполняет сетку всеми значениями
            tabsPanel.getSelectedGrid().fill();
        });

        // Поиск по тексту (Сотрудники: Имя, Электронная почта; Компании: все поля)
        searchPanel.getTextToSearch().addKeyPressListener(Key.ENTER, e -> {
            // Для вкладки сотрудников
            if (searchPanel.getColumnToSearch().isEnabled() && Objects.nonNull(searchPanel.getColumnToSearch().getValue())) {
                tabsPanel.getSelectedGrid()
                         .onSearch(searchPanel.getColumnToSearch().getValue(), searchPanel.getTextToSearch().getValue());
            }
            // Для вкладки компаний
            else {
                tabsPanel.getSelectedGrid()
                         .onSearch("", searchPanel.getTextToSearch().getValue());
            }
        });

        // Поиск по дате рождения
        searchPanel.getBirthDatePicker().addValueChangeListener(e -> {
            if (Objects.nonNull(searchPanel.getColumnToSearch().getValue())) {
                Optional.ofNullable(e.getValue())
                        .ifPresent(
                                birthDate ->
                                        tabsPanel.getSelectedGrid().onSearch(
                                                searchPanel.getColumnToSearch().getValue(),
                                                birthDate.toString()
                                        )

                        );
            }
        });

        // Поиск по компании
        searchPanel.getCompanyPicker().addValueChangeListener(e -> {
            if (Objects.nonNull(searchPanel.getColumnToSearch().getValue())) {
                Optional.ofNullable(e.getValue())
                        .ifPresent(
                                companyName ->
                                        tabsPanel.getSelectedGrid().onSearch(
                                                searchPanel.getColumnToSearch().getValue(),
                                                companyName
                                        )
                        );
            }
        });

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(buttonsPanel.getButtons(), searchPanel.getFilters());
        add(actions, tabsPanel.getTabs(), tabsPanel.getPages());
    }

    /**
     * Устанавливает вкладку компаний по умолчанию
     */
    private void setPreSelectedTab() {
        tabsPanel.setPreSelectedTab();
        searchPanel.showSearchColumn(false);
    }
}
