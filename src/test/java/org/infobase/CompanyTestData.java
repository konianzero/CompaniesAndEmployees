package org.infobase;

import org.infobase.model.Company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.infobase.model.BaseEntity.START_SEQ;

public class CompanyTestData {
    public static final TestMatcher<Company> COMPANY_MATCHER = TestMatcher.usingEqualsComparator(Company.class);

    public static final int COMPANY_1_ID = START_SEQ;

    public static final Company COMPANY_1 = new Company(COMPANY_1_ID, "Компания_1", "1234123412", "Улица 1", "79005554466");
    public static final Company COMPANY_2 = new Company(COMPANY_1_ID + 1, "Компания_2", "5678567856", "Проспект 2", "75008887799");

    public static final List<Company> ALL_COMPANIES = new ArrayList<>(Arrays.asList(COMPANY_1, COMPANY_2));

    public static Company getNew() {
        return new Company(null, "Новая Компания", "1111111111", "Новая Улица 1", "71002222222");
    }

    public static Company getUpdated() {
        return new Company(COMPANY_1_ID, "Компания_1", "5555555555", "Обновленная Улица 1", "79005554466");
    }
}
