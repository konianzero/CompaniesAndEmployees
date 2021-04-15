package org.infobase.web.component.grid;

import com.vaadin.flow.component.notification.Notification;

public interface OperationNotification {
    default void afterOperationNotification(String notificationMessage) {
        new Notification(
                notificationMessage,
                3000
        ).open();
    }
}
