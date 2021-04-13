package org.infobase.web.component.grid;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public interface OnRemoveNotification {

    default void preRemoveNotification(Runnable deleteCallback) {
        Button yes = new Button("Да");
        Button no = new Button("Нет");
        HorizontalLayout btnLayout = new HorizontalLayout(yes, no);
        btnLayout.setSpacing(true);
        Span text = new Span("Подтвердите удаление");

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
