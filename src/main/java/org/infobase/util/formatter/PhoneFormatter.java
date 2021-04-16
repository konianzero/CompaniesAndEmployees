package org.infobase.util.formatter;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.function.ValueProvider;
import org.infobase.model.Company;
import org.vaadin.textfieldformatter.CustomStringBlockFormatter;

import java.util.Optional;

public class PhoneFormatter {

    private PhoneFormatter() { }

    public static ValueProvider<Company, String> formatPhoneNumberProvider() {
        return company -> Optional.ofNullable(company.getPhoneNumber())
                                  .map(PhoneFormatter::formatPhoneNumber)
                                  .orElse("");
    }

    public static String formatPhoneNumber(String phone) {
        return Optional.ofNullable(phone)
                       .map(p -> String.format(
                               "+%s (%s%s%s) %s%s%s-%s%s-%s%s",
                               p.split("")
                               )
                       ).orElse("");
    }

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
