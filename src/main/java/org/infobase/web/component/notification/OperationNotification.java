package org.infobase.web.component.notification;

import com.vaadin.flow.component.notification.Notification;

public interface OperationNotification {
    default void afterOperationNotification(String notificationMessage) {
        Notification.show(
                notificationMessage,
                3000,
                Notification.Position.BOTTOM_START
        );
    }
}
