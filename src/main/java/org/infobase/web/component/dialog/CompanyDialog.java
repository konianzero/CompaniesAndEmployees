package org.infobase.web.component.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
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

    private TextField name = new TextField("", "Название");
    private TextField tin = new TextField("", "ИНН");
    private TextField address = new TextField("", "Адрес");
    private TextField phoneNumber = new TextField("", "Телефон");
    private Button saveBtn = new Button("Сохранить");
    private Button cancelBtn = new Button("Отменить");

    public CompanyDialog(CompanyService companyService) {
        this.companyService = companyService;

        binder = new BeanValidationBinder<>(Company.class);
        binder.bindInstanceFields(this);

        saveBtn.addClickListener(e -> save());
        cancelBtn.addClickListener(e -> close());

        VerticalLayout inputLayout = new VerticalLayout(name, tin, address, phoneNumber);
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
