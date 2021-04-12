package org.infobase.web.component.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import org.infobase.service.EmployeeService;
import org.infobase.to.EmployeeTo;

@SpringComponent
@UIScope
public class EmployeeDialog extends Dialog {

    private final EmployeeService employeeService;
    private final Binder<EmployeeTo> binder;

    private EmployeeTo employeeTo;
    private Runnable onSave;

    public EmployeeDialog(EmployeeService employeeService) {
        this.employeeService = employeeService;

        Button saveBtn = new Button("Сохранить");
        saveBtn.addClickListener(e -> save());

        Button cancelBtn = new Button("Отменить");
        cancelBtn.addClickListener(e -> close());

        DatePicker birthDate = new DatePicker("");
        birthDate.setPlaceholder("Дата Рождения");
        TextField name = new TextField("", "ФИО");
        TextField email = new TextField("", "Электронная почта");
        TextField companyName = new TextField("", "Компания");
        VerticalLayout inputLayout = new VerticalLayout(name, birthDate, email, companyName);

        HorizontalLayout actionLayout = new HorizontalLayout(saveBtn, cancelBtn);

        VerticalLayout layout = new VerticalLayout(inputLayout, actionLayout);
        add(layout);

        binder = new Binder<>(EmployeeTo.class);
        binder.forField(birthDate)
              .asRequired("Пожалуйста, выберите дату")
              .bind(EmployeeTo::getBirthDate, EmployeeTo::setBirthDate);
        binder.bindInstanceFields(this);
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
        binder.validate();
        employeeService.saveOrUpdate(employeeTo);
        onSave.run();
        close();
    }
}
