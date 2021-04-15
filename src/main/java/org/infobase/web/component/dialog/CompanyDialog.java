package org.infobase.web.component.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import org.infobase.model.Company;
import org.infobase.service.CompanyService;

@SpringComponent
@UIScope
public class CompanyDialog extends Dialog {

    private final CompanyService companyService;
    private final BeanValidationBinder<Company> binder;

    private Company company;
    private Runnable onSave;

    private Label nameLabel = new Label("Название");
    private Label tinLabel = new Label("ИНН");
    private Label addressLabel = new Label("Адрес");
    private Label phoneLabel = new Label("Телефон");

    private TextField name = new TextField("");
    private TextField tin = new TextField("");
    private TextField address = new TextField("");
    private TextField phoneNumber = new TextField("");
    private Button saveBtn = new Button("Сохранить");
    private Button cancelBtn = new Button("Отменить");

    public CompanyDialog(CompanyService companyService) {
        this.companyService = companyService;

        binder = new BeanValidationBinder<>(Company.class);
        binder.forField(phoneNumber)
              .withValidator(
                      new RegexpValidator(
                              "Указанный номер не соответствует формату",
                              "^(8|\\+7) \\(\\d{3}\\) \\d{3}-\\d{2}-\\d{2}$"
                      )
              )
              .bind(Company::getPhoneNumber, Company::setPhoneNumber);
        binder.bindInstanceFields(this);

        saveBtn.addClickListener(e -> save());
        cancelBtn.addClickListener(e -> close());

        VerticalLayout inputLayout = new VerticalLayout(nameLabel, name, tinLabel, tin, addressLabel, address, phoneLabel, phoneNumber);
        HorizontalLayout actionLayout = new HorizontalLayout(saveBtn, cancelBtn);
        VerticalLayout layout = new VerticalLayout(inputLayout, actionLayout);
        add(layout);
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

        companyService.saveOrUpdate(company);
        onSave.run();
        close();
    }
}
