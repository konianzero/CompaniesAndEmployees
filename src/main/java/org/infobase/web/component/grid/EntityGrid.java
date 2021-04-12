package org.infobase.web.component.grid;

import com.vaadin.flow.component.Component;

public interface EntityGrid extends OnRemoveNotification {
    Component getComponent();
    void fill();
    void onCreate();
    void onEdit();
    void onDelete();
}
