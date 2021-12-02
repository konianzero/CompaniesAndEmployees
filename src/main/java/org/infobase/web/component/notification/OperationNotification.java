package org.infobase.web.component.notification;

import com.vaadin.flow.component.notification.Notification;

/**
 * Интерфейс уведомления после операции
 */
public interface OperationNotification {
    /**
     * Уведомление после операции
     * @param notificationMessage текст уведомления
     */
    default void afterOperationNotification(String notificationMessage) {
        Notification.show(
                notificationMessage,
                3000,
                Notification.Position.BOTTOM_START
        );
    }
}
