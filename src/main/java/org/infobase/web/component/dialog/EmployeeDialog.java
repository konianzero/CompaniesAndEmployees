package org.infobase.web.component.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
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

@SpringComponent
@UIScope
public class EmployeeDialog extends Dialog {

    private final EmployeeService employeeService;
    private final BeanValidationBinder<EmployeeTo> binder;

    private EmployeeTo employeeTo;
    private Runnable onSave;

    private TextField name = new TextField("", "ФИО");
    private DatePicker birthDate = new DatePicker("");
    private TextField email = new TextField("", "Электронная почта");
    private TextField companyName = new TextField("", "Компания");
    private Button saveBtn = new Button("Сохранить");
    private Button cancelBtn = new Button("Отменить");

    public EmployeeDialog(EmployeeService employeeService) {
        this.employeeService = employeeService;

        birthDate.setPlaceholder("Дата Рождения");

        binder = new BeanValidationBinder<>(EmployeeTo.class);
        binder.forField(birthDate)
              .asRequired("Пожалуйста, выберите дату")
              .bind(EmployeeTo::getBirthDate, EmployeeTo::setBirthDate);
        binder.forField(email)
              .withValidator(new EmailValidator("Поле должно иметь формат адреса электронной почты"))
              .bind(EmployeeTo::getEmail, EmployeeTo::setEmail);
        binder.bindInstanceFields(this);

        saveBtn.addClickListener(e -> save());
        cancelBtn.addClickListener(e -> close());

        VerticalLayout inputLayout = new VerticalLayout(name, birthDate, email, companyName);
        HorizontalLayout actionLayout = new HorizontalLayout(saveBtn, cancelBtn);
        VerticalLayout layout = new VerticalLayout(inputLayout, actionLayout);
        add(layout);
    }

    public void setOnSave(Runnable onSave) {
        this.onSave = onSave;
    }

    public void edit(EmployeeTo employeeTo) {
        if (employeeTo == null) {
            close();
            return;
        }

        if (employeeTo.getId() != null) {
            this.employeeTo = employeeService.get(employeeTo.getId());
        } else {
            this.employeeTo = employeeTo;
        }

        binder.setBean(this.employeeTo);
    }

    private void save() {
        if (binder.validate().hasErrors()) { return; }

        employeeService.saveOrUpdate(employeeTo);
        onSave.run();
        close();
    }
}
