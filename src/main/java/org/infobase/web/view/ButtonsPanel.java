package org.infobase.web.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import lombok.Getter;

/**
 * Панель кнопок:
 * <ul>
 *      <li> Добавить
 *      <li> Редактировать
 *      <li> Удалить
 * </ul>
 */
@SpringComponent
@UIScope
@Getter
public class ButtonsPanel {
    /** Кнопка добавления */
    private final Button addBtn;
    /** Кнопка редактирования */
    private final Button editBtn;
    /** Кнопка удаления */
    private final Button delBtn;

    /** Контейнер для кнопок */
    private final HorizontalLayout buttons;

    /**
     * Инициализация понели кнопок
     */
    public ButtonsPanel() {
        addBtn = new Button("Добавить", VaadinIcon.PLUS.create());
        editBtn = new Button("Редактировать", VaadinIcon.PENCIL.create());
        delBtn = new Button("Удалить", VaadinIcon.MINUS.create());

        editBtn.setEnabled(false);
        delBtn.setEnabled(false);

        buttons = new HorizontalLayout(addBtn, editBtn, delBtn);
    }

    /**
     * Сделать видимыми и доступными кнопки редактирования и удаления
     * @param bool если true - отображать кнопки,
     *             false - не отображать
     */
    public void setEditAndDelEnabled(Boolean bool) {
        editBtn.setEnabled(bool);
        delBtn.setEnabled(bool);
    }
}
