package org.infobase.dao.mappers;

import org.infobase.model.Employee;
import org.jooq.Record9;
import org.jooq.RecordMapper;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

import org.infobase.model.Company;

@Component
public class EmployeeMapper implements RecordMapper<
        Record9<Integer, String, LocalDate, String, Integer, String, String, String, String>,
        Employee
        >
{
    @Override
    public Employee map(
            Record9<Integer, String, LocalDate, String, Integer, String, String, String, String> record
    ) {
        Employee employee = new Employee();
        employee.setId(record.value1());
        employee.setName(record.value2());
        employee.setBirthDate(record.value3());
        employee.setEmail(record.value4());
        employee.setCompany(
                new Company(
                        record.value5(),
                        record.value6(),
                        record.value7(),
                        record.value8(),
                        record.value9()
                )
        );
        return employee;
    }
}
