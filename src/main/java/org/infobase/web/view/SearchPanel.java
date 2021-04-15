package org.infobase.web.view;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import lombok.Getter;

import org.infobase.web.component.LocalizedDatePicker;

/**
 * Панель поиска, содержит:
 * <br>- Выпадающий список с названиями столбцов таблицы (Только для вкладки сотрудников)
 * <br><br>- Поля ввода:
 * <br>- Выпадающий список с названиями компаний (Только для вкладки сотрудников)
 * <br>- Поле выбора даты рождения (Только для вкладки сотрудников)
 * <br>- Поле для текстового поиска
 */
@SpringComponent
@UIScope
@Getter
public class SearchPanel {
    /** Выпадающий список с названиями столбцов таблицы */
    private final ComboBox<String> columnToSearch;
    /** Поле для текстового поиска */
    private final TextField textToSearch;
    /** Поле выбора даты рождения */
    private final LocalizedDatePicker birthDatePicker;
    /** Выпадающий список с названиями компаний */
    private final ComboBox<String> companyPicker;

    /** Контейнер для компонентов */
    private final HorizontalLayout filters;

    /**
     * Инициализация панели поиска
     */
    public SearchPanel() {
        columnToSearch = new ComboBox<>();
        columnToSearch.setPlaceholder("Выберите поле");
        columnToSearch.setRequired(true);
        columnToSearch.setClearButtonVisible(true);

        textToSearch = new TextField("", "Поиск");
        textToSearch.setClearButtonVisible(true);
        birthDatePicker = new LocalizedDatePicker();
        companyPicker = new ComboBox<>();

        birthDatePicker.setClearButtonVisible(false);
//        companyPicker.setClearButtonVisible(false);

        filters = new HorizontalLayout(columnToSearch, textToSearch, birthDatePicker, companyPicker);
    }

    /**
     * Активирует выпадающий список с названиями столбцов таблицы,
     * так же устанавливает видимость или очищает поле для текстового поиска
     * @param bool если true - включает выпадающий список с названиями столбцов таблицы,
     *             очищает поле текстового поиска и деактивирует поля ввода
     *             false - выключает выпадающий список и включает поле текстового поиска
     */
    public void showSearchColumn(boolean bool) {
        setSearchColumnVisibleAndEnable(bool);

        if (!bool) {
            showSearchText();
        } else {
            textToSearch.clear();
            disabledSearchInput();
        }
    }

    /**
     * Активирует поле выбора даты рождения
     */
    public void showDatePicker() {
        setDatePickerVisibleAndEnable(true);
        birthDatePicker.clear();

        setSearchTextVisibleAndEnable(false);
        setCompanyPickerVisibleAndEnable(false);
    }

    /**
     * Активирует поле текстового поиска
     */
    public void showSearchText() {
        setSearchTextVisibleAndEnable(true);
        textToSearch.clear();

        setDatePickerVisibleAndEnable(false);
        setCompanyPickerVisibleAndEnable(false);
    }

    /**
     * Активирует поле выбора компании
     */
    public void showCompanyPicker() {
        setCompanyPickerVisibleAndEnable(true);
        companyPicker.clear();

        setSearchTextVisibleAndEnable(false);
        setDatePickerVisibleAndEnable(false);
    }

    /**
     * Установить видимость и активность выпадающего списка с названиями столбцов таблицы
     * @param bool если true - выпадающий список видим и активен;
     *             false - не видим и не активен
     */
    private void setSearchColumnVisibleAndEnable(boolean bool) {
        columnToSearch.setEnabled(bool);
        columnToSearch.setVisible(bool);
    }

    /**
     * Установить видимость и активность поля выбора даты рождения
     * @param bool если true - поле видимо и активно;
     *             false - не видимо и не активно
     */
    private void setDatePickerVisibleAndEnable(boolean bool) {
        birthDatePicker.setEnabled(bool);
        birthDatePicker.setVisible(bool);
    }

    /**
     * Установить видимость и активность поля для текстового поиска
     * @param bool если true - поле видимо и активно;
     *             false - не видимо и не активно
     */
    private void setSearchTextVisibleAndEnable(boolean bool) {
        textToSearch.setEnabled(bool);
        textToSearch.setVisible(bool);
    }

    /**
     * Установить видимость и активность выпадающего списка с названиями компаний
     * @param bool если true - выпадающий список видим и активен;
     *             false - не видим и не активен
     */
    private void setCompanyPickerVisibleAndEnable(boolean bool) {
        companyPicker.setEnabled(bool);
        companyPicker.setVisible(bool);
    }

    /**
     * Деактивирует поля ввода
     */
    public void disabledSearchInput() {
        textToSearch.setEnabled(false);
        birthDatePicker.setEnabled(false);
        companyPicker.setEnabled(false);
    }

    /**
     * Очищает поля ввода
     */
    public void clearSearchInput() {
        textToSearch.clear();
        birthDatePicker.clear();
        companyPicker.clear();
    }
}
