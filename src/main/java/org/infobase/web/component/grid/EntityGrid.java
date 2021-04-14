package org.infobase.web.component.grid;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;

import java.util.Collection;
import java.util.function.Consumer;

public abstract class EntityGrid<T> extends Grid<T> implements OnRemoveNotification {
    public abstract Component getComponent();
    public abstract Collection<String> getHeaders();
    public abstract void fill();
    public abstract void onCreate();
    public abstract void onEdit();
    public abstract void onSearch(String columnHeader, String textToSearch);
    public abstract void onDelete();

    private Consumer<Boolean> enableEditButtons;

    public void setEnableEditButtons(Consumer<Boolean> enableEditButtons) {
        this.enableEditButtons = enableEditButtons;
    }

    public void disableEditButtons() {
        enableEditButtons.accept(false);
    }

    protected void setSelectionListener() {
        addSelectionListener(e -> e.getFirstSelectedItem()
                .ifPresentOrElse(
                        c -> enableEditButtons.accept(true),
                        () -> enableEditButtons.accept(false)
                ));
    }
}
