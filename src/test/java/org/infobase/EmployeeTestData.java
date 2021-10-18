package org.infobase;

import org.infobase.model.Employee;
import org.infobase.to.EmployeeTo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.infobase.CompanyTestData.COMPANY_1;
import static org.infobase.CompanyTestData.COMPANY_2;
import static org.infobase.model.BaseEntity.START_SEQ;

public class EmployeeTestData {
    public static final TestMatcher<EmployeeTo> EMPLOYEE_TO_MATCHER = TestMatcher.usingEqualsComparator(EmployeeTo.class);

    public static final int EMPLOYEE_1_ID = START_SEQ + 2;

    public static final Employee EMPLOYEE_1 = new Employee(EMPLOYEE_1_ID,
            "Иванов Иван Иванович",
            LocalDate.of(2000, 1, 1),
            "ivan@mail.com", COMPANY_1);
    public static final Employee EMPLOYEE_2 = new Employee(EMPLOYEE_1_ID + 1,
            "Петров Петр Петрович",
            LocalDate.of(2000, 2, 2),
            "petr@mail.com", COMPANY_2);

    public static final List<Employee> ALL_EMPLOYEES = new ArrayList<>(Arrays.asList(EMPLOYEE_1, EMPLOYEE_2));

    public static Employee getNew() {
        return new Employee(null,
                "Денисов Иннокентий Антонович",
                LocalDate.of(2002, 5, 5),
                "den@mail.com", COMPANY_2);

    }

    public static Employee getUpdated() {
        return new Employee(EMPLOYEE_1_ID,
                "Иванов Иван Иванович",
                LocalDate.of(2000, 1, 1),
                "vanvan@mail.com", COMPANY_2);
    }
}
