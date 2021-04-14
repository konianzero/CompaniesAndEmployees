package org.infobase.web.view;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.infobase.web.component.CompanyComboBox;
import org.infobase.web.component.LocalizedDatePicker;

@SpringComponent
@UIScope
public class SearchPanel {

    final ComboBox<String> searchColumn;
    final TextField searchText;
    final LocalizedDatePicker birthDatePicker;

    final HorizontalLayout filter;

    public SearchPanel() {
        searchColumn = new ComboBox<>();
        searchColumn.setPlaceholder("Выберите поле");
        searchColumn.setRequired(true);
        searchColumn.setClearButtonVisible(true);

        searchText = new TextField("", "Поиск");
        birthDatePicker = new LocalizedDatePicker();

        setSearchTextVisibleAndEnable(true);

        filter = new HorizontalLayout(searchColumn, searchText, birthDatePicker);
    }

    public void setDatePickerVisibleAndEnable(boolean bool) {
        birthDatePicker.setEnabled(bool);
        birthDatePicker.setVisible(bool);

        if (bool) {
            setSearchTextVisibleAndEnable(false);
        }
    }

    public void setSearchTextVisibleAndEnable(boolean bool) {
        searchText.setEnabled(bool);
        searchText.setVisible(bool);

        if (bool) {
            setDatePickerVisibleAndEnable(false);
        }
    }
}
