package org.infobase.util.converter;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.time.LocalDate;
import java.util.Optional;

public class StringToDateConverter implements Converter<String, LocalDate> {
    @Override
    public Result<LocalDate> convertToModel(String s, ValueContext valueContext) {
        return Result.ok(LocalDate.parse(s));
    }

    @Override
    public String convertToPresentation(LocalDate localDate, ValueContext valueContext) {
        return Optional.ofNullable(localDate).orElse(LocalDate.MIN).toString();
    }
}
