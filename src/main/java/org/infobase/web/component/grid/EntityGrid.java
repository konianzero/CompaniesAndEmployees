package org.infobase.web.component.grid;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import org.infobase.model.Company;

public interface EntityGrid {
    Component getComponent();
    void fill();
    void onCreate();
    void onEdit();
    void onDelete();
}
