package org.infobase.util.formatter;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.function.ValueProvider;
import org.infobase.model.Company;
import org.vaadin.textfieldformatter.CustomStringBlockFormatter;

import java.util.Optional;

/**
 * Утилитный класс для форматирования телефонного номера
 */
public class PhoneFormatter {

    private PhoneFormatter() { }

    /**
     * Провайдер форматированного телефонного номера для метода добавления колонки в Grid
     * @return Провайдер форматированного телефонного номера
     */
    public static ValueProvider<Company, String> formatPhoneNumberProvider() {
        return company -> Optional.ofNullable(company.getPhoneNumber())
                                  .map(PhoneFormatter::formatPhoneNumber)
                                  .orElse("");
    }

    /**
     * Форматирует телефонный номер хранящийся в базе по шаблону +# (###) ###-##-##
     * @param phone телефонный номер в строковом формате
     * @return форматированный номер или пустую строку если телефонный номер пустой
     */
    public static String formatPhoneNumber(String phone) {
        return Optional.ofNullable(phone)
                       .map(p -> String.format(
                               "+%s (%s%s%s) %s%s%s-%s%s-%s%s",
                               p.split("")
                               )
                       ).orElse("");
    }

    /**
     * Добавляет маску ввода на поле ввода телефонного номера
     * @param phoneNumber поле ввода телефонного номера
     */
    public static void addPhoneNumberInputMask(TextField phoneNumber) {
        CustomStringBlockFormatter.Options options = new CustomStringBlockFormatter.Options();
        options.setPrefix("+", false);
        options.setBlocks(2,3,3,2,2);
        options.setDelimiters(" (",") ","-", "-");
        options.setNumericOnly(true);
        CustomStringBlockFormatter phone = new CustomStringBlockFormatter(options);
        phone.extend(phoneNumber);
    }
}
