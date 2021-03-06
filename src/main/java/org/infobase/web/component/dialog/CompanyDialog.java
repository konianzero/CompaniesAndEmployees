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
import org.infobase.util.formatter.PhoneFormatter;
import org.infobase.web.component.notification.OperationNotification;

import java.util.Optional;

/**
 * Диалоговое окно для редактирования данных компаний
 */
@SpringComponent
@UIScope
public class CompanyDialog extends Dialog implements OperationNotification {

    /** Класс бизнес логики для сущностей компаний */
    private final CompanyService companyService;
    /** Класс для валидации сущности компании */
    private final BeanValidationBinder<Company> binder = new BeanValidationBinder<>(Company.class);

    /** Класс сущности компании */
    private Company company;
    /** Действие для выполнения после сохранения */
    private Runnable onSave;

    /* Поля и кнопки */
    private TextField name = new TextField("Название");
    private TextField tin = new TextField("ИНН");
    private TextField address = new TextField("Адрес");
    private TextField phoneNumber = new TextField("Телефон");
    private Button saveBtn = new Button("Сохранить");
    private Button cancelBtn = new Button("Отменить");

    public CompanyDialog(CompanyService companyService) {
        this.companyService = companyService;

        PhoneFormatter.addPhoneNumberInputMask(phoneNumber);
        binding();

        saveBtn.addClickListener(e -> save());
        cancelBtn.addClickListener(e -> close());

        VerticalLayout inputLayout = new VerticalLayout(name, tin, address, phoneNumber);
        HorizontalLayout actionLayout = new HorizontalLayout(saveBtn, cancelBtn);
        VerticalLayout layout = new VerticalLayout(inputLayout, actionLayout);
        add(layout);
    }

    /**
     * Валидация телефонного номера и ИНН
     */
    private void binding() {
        binder.forField(tin)
                .withValidator(
                        new RegexpValidator(
                                "Не соответствует формату, необходимо ввести 10 цифр",
                                "\\d{10}"
                        )
                )
                .bind(Company::getTin, Company::setTin);
        binder.forField(phoneNumber)
                .withValidator(
                        new RegexpValidator(
                                "Не соответствует формату: +7 (###) ###-##-##",
                                "^\\+7 \\(\\d{3}\\) \\d{3}-\\d{2}-\\d{2}$"
                        )
                )
                .withConverter(
                        p -> Optional.ofNullable(p)
                                     .map(phone -> phone.replaceAll("[^0-9]", ""))
                                     .orElse(""),
                        PhoneFormatter::formatPhoneNumber
                )
                .bind(Company::getPhoneNumber, Company::setPhoneNumber);
        binder.bindInstanceFields(this);
    }

    /**
     * Установка действия для выполнения после сохранения
     * @param onSave действия для выполнения после сохранения
     */
    public void setOnSave(Runnable onSave) {
        this.onSave = onSave;
    }

    /**
     * Редактирование сущности компании
     * @param company сущность компании
     */
    public void edit(Company company) {
        if (company == null) {
            close();
            return;
        }

        PhoneFormatter.formatPhoneNumber("79001234567");

        if (!company.isNew()) {
            this.company = companyService.get(company.getId());
        } else {
            this.company = company;
        }

        binder.setBean(this.company);
    }

    /**
     * Сохранение сущности компании
     */
    private void save() {
        if (binder.validate().hasErrors()) { return; }

        if (companyService.saveOrUpdate(company) != 0) {
            afterOperationNotification(company.isNew() ? "Компания добавлена!" : "Компания отредактирована!");
            onSave.run();
        } else {
            afterOperationNotification("Произошла ошибка при добавлении/редактировании компании!");
        }
        close();
    }
}
