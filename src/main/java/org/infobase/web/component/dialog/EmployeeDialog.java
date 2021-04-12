package org.infobase.web.component.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import org.infobase.service.EmployeeService;
import org.infobase.to.EmployeeTo;
import org.infobase.util.converter.StringToDateConverter;

@SpringComponent
@UIScope
public class EmployeeDialog extends Dialog {

    private final EmployeeService employeeService;
    private final Binder<EmployeeTo> binder;

    private EmployeeTo employeeTo;
    private Runnable onSave;

    private TextField name = new TextField("", "ФИО");
    private TextField birthDate = new TextField("", "Дата Рождения");
    private TextField email = new TextField("", "Электронная почта");
    private TextField companyName = new TextField("", "Компания");
    private Button saveBtn = new Button("Сохранить");
    private Button cancelBtn = new Button("Отменить");

    public EmployeeDialog(EmployeeService employeeService) {
        this.employeeService = employeeService;

        binder = new Binder<>(EmployeeTo.class);
        binder.forField(birthDate)
              .withConverter(new StringToDateConverter())
              .bind(EmployeeTo::getBirthDate, EmployeeTo::setBirthDate);
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
        employeeService.saveOrUpdate(employeeTo);
        onSave.run();
        close();
    }
}
