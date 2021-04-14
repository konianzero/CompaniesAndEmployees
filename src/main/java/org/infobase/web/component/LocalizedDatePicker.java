package org.infobase.web.component;

import com.vaadin.flow.component.datepicker.DatePicker;

import java.util.Arrays;
import java.util.Locale;

public class LocalizedDatePicker extends DatePicker {

    public LocalizedDatePicker() {
        localize();
    }

    private void localize() {
        setLocale(new Locale("ru"));
        setI18n(new DatePicker.DatePickerI18n()
                .setWeek("неделя")
                .setCalendar("календарь").setClear("очистить")
                .setToday("сегодня").setCancel("отменять").setFirstDayOfWeek(1)
                .setMonthNames(Arrays.asList("январь", "февраль", "март",
                        "апрель", "май", "июнь", "июль", "август",
                        "сентябрь", "октябрь", "ноябрь", "декабрь"))
                .setWeekdays(Arrays.asList("понедельник", "вторник", "среда",
                        "четверг", "пятница", "суббота", "воскресенье"))
                .setWeekdaysShort(Arrays.asList("пн", "вт", "ср", "чт", "пт",
                        "сб", "вс"))
        );
    }
}
