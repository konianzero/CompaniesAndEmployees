package org.infobase.web.view;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class SearchPanel {

    final ComboBox<String> searchColumn;
    final TextField searchText;
    final DatePicker birthDatePicker;

    final HorizontalLayout filter;

    public SearchPanel() {
        searchColumn = new ComboBox<>();
        searchColumn.setPlaceholder("Выберите поле");
        searchColumn.setRequired(true);
        searchColumn.setClearButtonVisible(true);

        searchText = new TextField("", "Поиск");
        searchText.setEnabled(true);
        searchText.setVisible(true);

        birthDatePicker = new DatePicker("");
        birthDatePicker.setEnabled(false);
        birthDatePicker.setVisible(false);

        filter = new HorizontalLayout(searchColumn, searchText, birthDatePicker);
    }

    public void setDatePickerVisibleAndEnable(boolean bool) {
        birthDatePicker.setEnabled(bool);
        birthDatePicker.setVisible(bool);
    }

    public void setSearchTextVisibleAndEnable(boolean bool) {
        searchText.setEnabled(bool);
        searchText.setVisible(bool);
    }
}
