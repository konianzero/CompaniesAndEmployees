package org.infobase.web.component;

import com.vaadin.flow.component.combobox.ComboBox;

import java.util.List;

public class CompanyComboBox<T> extends ComboBox<T> {

    public CompanyComboBox() {
        setPlaceholder("Компания");
        setClearButtonVisible(true);
    }

    public void setCompaniesNamesAsItems(List<T> companiesNames) {
        setItems(companiesNames);
    }
}
