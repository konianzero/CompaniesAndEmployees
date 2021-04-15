package org.infobase.web.view;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import lombok.Getter;

import org.infobase.web.component.CompanyComboBox;
import org.infobase.web.component.LocalizedDatePicker;

@SpringComponent
@UIScope
@Getter
public class SearchPanel {

    private final ComboBox<String> columnToSearch;
    private final TextField textToSearch;
    private final LocalizedDatePicker birthDatePicker;
    private final CompanyComboBox<String> companyPicker;

    private final HorizontalLayout filters;

    public SearchPanel() {
        columnToSearch = new ComboBox<>();
        columnToSearch.setPlaceholder("Выберите поле");
        columnToSearch.setRequired(true);
        columnToSearch.setClearButtonVisible(true);

        textToSearch = new TextField("", "Поиск");
        textToSearch.setClearButtonVisible(true);
        birthDatePicker = new LocalizedDatePicker();
        companyPicker = new CompanyComboBox<>();

        filters = new HorizontalLayout(columnToSearch, textToSearch, birthDatePicker, companyPicker);
    }

    public void showSearchColumn(boolean bool) {
        setSearchColumnVisibleAndEnable(bool);

        if (!bool) {
            showSearchText();
        } else {
            textToSearch.clear();
        }
    }

    public void showDatePicker() {
        setDatePickerVisibleAndEnable(true);
        birthDatePicker.clear();

        setSearchTextVisibleAndEnable(false);
        setCompanyPickerVisibleAndEnable(false);
    }

    public void showSearchText() {
        setSearchTextVisibleAndEnable(true);
        textToSearch.clear();

        setDatePickerVisibleAndEnable(false);
        setCompanyPickerVisibleAndEnable(false);
    }

    public void showCompanyPicker() {
        setCompanyPickerVisibleAndEnable(true);
        companyPicker.clear();

        setSearchTextVisibleAndEnable(false);
        setDatePickerVisibleAndEnable(false);
    }

    private void setSearchColumnVisibleAndEnable(boolean bool) {
        columnToSearch.setEnabled(bool);
        columnToSearch.setVisible(bool);
    }

    private void setDatePickerVisibleAndEnable(boolean bool) {
        birthDatePicker.setEnabled(bool);
        birthDatePicker.setVisible(bool);
    }

    private void setSearchTextVisibleAndEnable(boolean bool) {
        textToSearch.setEnabled(bool);
        textToSearch.setVisible(bool);
    }

    private void setCompanyPickerVisibleAndEnable(boolean bool) {
        companyPicker.setEnabled(bool);
        companyPicker.setVisible(bool);
    }

    public void disabledSearchInput() {
        textToSearch.setEnabled(false);
        birthDatePicker.setEnabled(false);
        companyPicker.setEnabled(false);
    }

    public void clearSearchInput() {
        textToSearch.clear();
        birthDatePicker.clear();
        companyPicker.clear();
    }
}
