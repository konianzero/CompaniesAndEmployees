package org.infobase.web.component.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import org.infobase.model.Company;
import org.infobase.service.CompanyService;
import org.infobase.web.component.grid.OperationNotification;

@SpringComponent
@UIScope
public class CompanyDialog extends Dialog implements OperationNotification {

    private final CompanyService companyService;
    private final BeanValidationBinder<Company> binder = new BeanValidationBinder<>(Company.class);

    private Company company;
    private Runnable onSave;

    private TextField name = new TextField("Название");
    private TextField tin = new TextField("ИНН");
    private TextField address = new TextField("Адрес");
    private TextField phoneNumber = new TextField("Телефон");
    private Button saveBtn = new Button("Сохранить");
    private Button cancelBtn = new Button("Отменить");

    public CompanyDialog(CompanyService companyService) {
        this.companyService = companyService;

        binding();

        saveBtn.addClickListener(e -> save());
        cancelBtn.addClickListener(e -> close());

        VerticalLayout inputLayout = new VerticalLayout(name, tin, address,  phoneNumber);
        HorizontalLayout actionLayout = new HorizontalLayout(saveBtn, cancelBtn);
        VerticalLayout layout = new VerticalLayout(inputLayout, actionLayout);
        add(layout);
    }

    public void binding() {
        binder.forField(phoneNumber)
                .withValidator(
                        new RegexpValidator(
                                "Указанный номер не соответствует формату",
                                "^(8|\\+7) \\(\\d{3}\\) \\d{3}-\\d{2}-\\d{2}$"
                        )
                )
                .bind(Company::getPhoneNumber, Company::setPhoneNumber);
        binder.bindInstanceFields(this);
    }

    public void setOnSave(Runnable onSave) {
        this.onSave = onSave;
    }

    public void edit(Company company) {
        if (company == null) {
            close();
            return;
        }

        if (company.getId() != null) {
            this.company = companyService.get(company.getId());
        } else {
            this.company = company;
        }

        binder.setBean(this.company);
    }

    private void save() {
        if (binder.validate().hasErrors()) { return; }

        if (companyService.saveOrUpdate(company) != 0) {
            afterOperationNotification("Компания добавлена!");
            onSave.run();
        } else {
            afterOperationNotification("Произошла ошибка при добавлении/редактировании компании!");
        }
        close();
    }
}
