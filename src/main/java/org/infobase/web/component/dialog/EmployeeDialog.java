package org.infobase.web.component.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
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
import org.infobase.web.component.grid.OperationNotification;

import java.util.List;

@SpringComponent
@UIScope
public class EmployeeDialog extends Dialog implements OperationNotification {

    private final EmployeeService employeeService;
    private final BeanValidationBinder<EmployeeTo> binder;

    private EmployeeTo employeeTo;
    private Runnable onSave;

    private Label nameLabel = new Label("ФИО");
    private Label birthdayLabel = new Label("Дата Рождения");
    private Label emailLabel = new Label("Электронная почта");
    private Label companyLabel = new Label("Компания");

    private TextField name = new TextField("");
    private LocalizedDatePicker birthDate = new LocalizedDatePicker();
    private TextField email = new TextField("");
    private ComboBox<String> companyName = new ComboBox<>();
    private Button saveBtn = new Button("Сохранить");
    private Button cancelBtn = new Button("Отменить");

    public EmployeeDialog(EmployeeService employeeService) {
        this.employeeService = employeeService;

        binder = new BeanValidationBinder<>(EmployeeTo.class);
        binder.forField(birthDate)
              .asRequired("Пожалуйста, выберите дату")
              .bind(EmployeeTo::getBirthDate, EmployeeTo::setBirthDate);
        binder.forField(email)
              .asRequired("Пожалуйста, укажите электронный адрес")
              .withValidator(new EmailValidator("Не соответствует формату адреса электронной почты"))
              .bind(EmployeeTo::getEmail, EmployeeTo::setEmail);
        binder.bindInstanceFields(this);

        saveBtn.addClickListener(e -> save());
        cancelBtn.addClickListener(e -> close());

        VerticalLayout inputLayout = new VerticalLayout(nameLabel, name, birthdayLabel, birthDate, emailLabel, email, companyLabel, companyName);
        HorizontalLayout actionLayout = new HorizontalLayout(saveBtn, cancelBtn);
        VerticalLayout layout = new VerticalLayout(inputLayout, actionLayout);
        add(layout);
    }

    public void setItemsToCompanyComboBox(List<String> companiesNames) {
        companyName.setItems(companiesNames);
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

        if (employeeService.saveOrUpdate(employeeTo) != 0) {
            afterOperationNotification("Сотрудник добавлен!");
            onSave.run();
        } else {
            afterOperationNotification("Произошла ошибка при добавлении/редактировании сотрудника!");
        }
        close();
    }
}
