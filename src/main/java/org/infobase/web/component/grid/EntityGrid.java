package org.infobase.web.component.grid;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import org.infobase.web.component.notification.OnRemoveNotification;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Базовый класс таблицы
 * @param <T> тип записей в таблице
 */
public abstract class EntityGrid<T> extends Grid<T> implements OnRemoveNotification {
    /** Получить имя таблицы */
    public abstract String getName();
    /** Получить эту таблицу */
    public abstract Component getComponent();
    /** Получить заголовки таблицы */
    public abstract Collection<String> getHeaders();
    /** Заполнить таблицу данными */
    public abstract void fill();
    /** Действие при создании новой записи в таблице */
    public abstract void onCreate();
    /** Действие при редактировании записи в таблице */
    public abstract void onEdit();
    /** Действие при поиске в таблице */
    public abstract void onSearch(String columnHeader, String textToSearch);
    /** Действие при удалении записи из таблице */
    public abstract void onDelete();

    /** Действие по отображение кнопок редактирования */
    private Consumer<Boolean> enableEditButtons;

    /** Установить действие по отображению кнопок редактирования */
    public void onEnableEditButtons(Consumer<Boolean> enableEditButtons) {
        this.enableEditButtons = enableEditButtons;
    }

    /**
     * Не отображать кнопки редактирования
     */
    public void disableEditButtons() {
        enableEditButtons.accept(false);
    }

    /**
     * Отображение кнопок редактирования при выборе записи в таблице
     */
    protected void setSelectionListener() {
        addSelectionListener(e -> e.getFirstSelectedItem()
                .ifPresentOrElse(
                        c -> enableEditButtons.accept(true),
                        () -> enableEditButtons.accept(false)
                ));
    }
}
