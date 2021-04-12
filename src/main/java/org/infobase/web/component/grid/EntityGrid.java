package org.infobase.web.component.grid;

import com.vaadin.flow.component.Component;

import java.util.Collection;

public interface EntityGrid extends OnRemoveNotification {
    Component getComponent();
    Collection<String> getHeaders();
    void fill();
    void onCreate();
    void onEdit();
    void onSearch(String columnHeader, String textToSearch);
    void onDelete();
}
