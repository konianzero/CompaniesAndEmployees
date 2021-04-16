package org.infobase.web.component.grid;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum EmployeeHeaders {
    ID          { @Override public String getHeader() { return "ID";                } },
    FULL_NAME   { @Override public String getHeader() { return "ФИО";               } },
    BIRTH_DATE  { @Override public String getHeader() { return "Дата Рождения";     } },
    EMAIL       { @Override public String getHeader() { return "Электронная почта"; } },
    COMPANY     { @Override public String getHeader() { return "Компания";          } };

    public abstract String getHeader();

    private static Stream<EmployeeHeaders> getValues() {
        return Arrays.stream(values()).skip(1);
    }

    public static EmployeeHeaders fromGridHeader(String text) {
        return getValues()
                     .filter(v -> v.getHeader().equals(text))
                     .findFirst().get();
    }

    public static List<String> getGridHeaders() {
        return getValues()
                     .map(EmployeeHeaders::getHeader)
                     .collect(Collectors.toList());
    }
}
