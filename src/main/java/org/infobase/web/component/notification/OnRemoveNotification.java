package org.infobase.web.component.notification;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 * Интерфейс уведомления при удалении
 */
public interface OnRemoveNotification {
    /**
     * Уведомить об удалении и выполнить переданное действие
     * @param deleteCallback действие при удалении
     * @param notificationMessage текст уведомления
     */
    default void preRemoveNotification(Runnable deleteCallback, String notificationMessage) {
        Button yes = new Button("Да");
        Button no = new Button("Нет");
        HorizontalLayout btnLayout = new HorizontalLayout(yes, no);
        btnLayout.setSpacing(true);
        Label text = new Label(notificationMessage + " Подтвердите удаление.");

        Notification notification = new Notification(text, btnLayout);
        notification.setPosition(Notification.Position.MIDDLE);
        notification.open();

        yes.addClickListener(event -> {
            deleteCallback.run();
            notification.close();
        });
        no.addClickListener(event -> notification.close());
    }
}
