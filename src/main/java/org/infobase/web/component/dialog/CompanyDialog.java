package org.infobase.web.component.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import org.infobase.model.Company;
import org.infobase.service.CompanyService;

@SpringComponent
@UIScope
public class CompanyDialog extends Dialog {

    private final CompanyService companyService;
    private final Binder<Company> binder;

    private Company company;
    private Runnable onSave;

    public CompanyDialog(CompanyService companyService) {
        this.companyService = companyService;

        Button saveBtn = new Button("Сохранить");
        saveBtn.addClickListener(e -> save());

        Button cancelBtn = new Button("Отменить");
        cancelBtn.addClickListener(e -> close());

        TextField name = new TextField("", "Название");
        TextField tin = new TextField("", "ИНН");
        TextField address = new TextField("", "Адрес");
        TextField phoneNumber = new TextField("", "Телефон");
        VerticalLayout inputLayout = new VerticalLayout(name, tin, address, phoneNumber);

        HorizontalLayout actionLayout = new HorizontalLayout(saveBtn, cancelBtn);

        VerticalLayout layout = new VerticalLayout(inputLayout, actionLayout);
        add(layout);

        binder = new Binder<>(Company.class);
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
        companyService.saveOrUpdate(company);
        onSave.run();
        close();
    }
}
