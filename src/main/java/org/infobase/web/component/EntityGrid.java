package org.infobase.web.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import org.infobase.model.Company;

public interface EntityGrid {
    Component getComponent();
    void fill();
}
