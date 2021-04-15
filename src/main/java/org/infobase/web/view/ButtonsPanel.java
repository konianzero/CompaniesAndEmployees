package org.infobase.web.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import lombok.Getter;

@SpringComponent
@UIScope
@Getter
public class ButtonsPanel {

    private final Button addBtn;
    private final Button editBtn;
    private final Button delBtn;

    private final HorizontalLayout buttons;

    public ButtonsPanel() {
        addBtn = new Button("Добавить", VaadinIcon.PLUS.create());
        editBtn = new Button("Редактировать", VaadinIcon.PENCIL.create());
        delBtn = new Button("Удалить", VaadinIcon.MINUS.create());

        editBtn.setEnabled(false);
        delBtn.setEnabled(false);

        buttons = new HorizontalLayout(addBtn, editBtn, delBtn);
    }

    public void setEditAndDelEnabled(Boolean bool) {
        editBtn.setEnabled(bool);
        delBtn.setEnabled(bool);
    }
}
