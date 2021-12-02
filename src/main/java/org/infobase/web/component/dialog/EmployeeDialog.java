package org.infobase.web.component.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import org.infobase.service.EmployeeService;
import org.infobase.to.EmployeeTo;
import org.infobase.web.component.LocalizedDatePicker;
import org.infobase.web.component.notification.OperationNotification;

import java.util.List;

/**
 * Диалоговое окно для редактирования данных сотрудников
 */
@SpringComponent
@UIScope
public class EmployeeDialog extends Dialog implements OperationNotification {

    /** Класс бизнес логики для сущностей сотрудников */
    private final EmployeeService employeeService;
    /** Класс для валидации сущности сотрудника */
    private final BeanValidationBinder<EmployeeTo> binder = new BeanValidationBinder<>(EmployeeTo.class);

    /** Класс сущности сотрудника */
    private EmployeeTo employeeTo;
    /** Действие для выполнения после сохранения */
    private Runnable onSave;

    /* Поля и кнопки */
    private TextField name = new TextField("ФИО");
    private LocalizedDatePicker birthDate = new LocalizedDatePicker();
    private TextField email = new TextField("Электронная почта");
    private ComboBox<String> companyName = new ComboBox<>();
    private Button saveBtn = new Button("Сохранить");
    private Button cancelBtn = new Button("Отменить");

    public EmployeeDialog(EmployeeService employeeService) {
        this.employeeService = employeeService;

        birthDate.setLabel("Дата Рождения");
        companyName.setLabel("Компания");
        companyName.setClearButtonVisible(true);

        binding();

        saveBtn.addClickListener(e -> save());
        cancelBtn.addClickListener(e -> close());

        VerticalLayout inputLayout = new VerticalLayout(name, birthDate, email, companyName);
        HorizontalLayout actionLayout = new HorizontalLayout(saveBtn, cancelBtn);
        VerticalLayout layout = new VerticalLayout(inputLayout, actionLayout);
        add(layout);
    }

    /**
     * Валидация даты рождения и электронной почты
     */
    public void binding() {
        binder.forField(birthDate)
                .asRequired("Пожалуйста, выберите дату")
                .bind(EmployeeTo::getBirthDate, EmployeeTo::setBirthDate);
        binder.forField(email)
                .asRequired("Пожалуйста, укажите электронный адрес")
                .withValidator(new EmailValidator("Не соответствует формату адреса электронной почты"))
                .bind(EmployeeTo::getEmail, EmployeeTo::setEmail);
        binder.bindInstanceFields(this);
    }

    /**
     * Установка имен компаний в выпадающий список
     * @param companiesNames
     */
    public void setItemsToCompanyComboBox(List<String> companiesNames) {
        companyName.setItems(companiesNames);
    }

    /**
     * Установка действия для выполнения после сохранения
     * @param onSave действия для выполнения после сохранения
     */
    public void setOnSave(Runnable onSave) {
        this.onSave = onSave;
    }

    /**
     * Редактирование сущности сотрудника
     * @param employeeTo сущность сотрудника для отображения в интерфейсе
     */
    public void edit(EmployeeTo employeeTo) {
        if (employeeTo == null) {
            close();
            return;
        }

        if (!employeeTo.isNew()) {
            this.employeeTo = employeeService.get(employeeTo.getId());
        } else {
            this.employeeTo = employeeTo;
        }

        binder.setBean(this.employeeTo);
    }

    /**
     * Сохранение сущности сотрудника
     */
    private void save() {
        if (binder.validate().hasErrors()) { return; }

        if (employeeService.saveOrUpdate(employeeTo) != 0) {
            afterOperationNotification(employeeTo.isNew() ? "Сотрудник добавлен!" : "Сотрудник отредактирован!");
            onSave.run();
        } else {
            afterOperationNotification("Произошла ошибка при добавлении/редактировании сотрудника!");
        }
        close();
    }
}
