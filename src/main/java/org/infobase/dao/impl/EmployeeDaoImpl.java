package org.infobase.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.jooq.*;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.infobase.model.Employee;
import org.infobase.dao.EmployeeDao;
import org.infobase.web.component.grid.EmployeeHeaders;
import org.infobase.dao.mappers.EmployeeMapper;
import org.infobase.db.generated.tables.Companies;
import org.infobase.db.generated.tables.Employees;

import static org.infobase.db.generated.Tables.COMPANIES;
import static org.infobase.db.generated.Tables.EMPLOYEES;

@Repository
@Slf4j
@RequiredArgsConstructor
public class EmployeeDaoImpl implements EmployeeDao {

    private final DSLContext dslContext;
    private final EmployeeMapper mapper;

    private Employees e = EMPLOYEES.as("e");
    private Companies c = COMPANIES.as("c");
    private SelectOnConditionStep<
            Record9<Integer, String, LocalDate, String, Integer, String, String, String, String>
            > selectJoin() {
        return dslContext.select(e.ID.as("emp_id"), e.NAME.as("emp_name"), e.BIRTH_DATE, e.EMAIL,
                c.ID.as("comp_id"), c.NAME.as("comp_name"), c.TIN, c.ADDRESS, c.PHONE_NUMBER)
                .from(e)
                .leftJoin(c).on(c.ID.eq(e.COMPANY_ID));
    }

    @Transactional
    public int save(Employee employee) {
        int id = 0;
        try {
            /* TODO - Don't insert, cause - ?
            InsertQuery<EmployeesRecord> insertQuery = dslContext.insertQuery(EMPLOYEES);
            insertQuery.addValues(getMap(employee));
            id = insertQuery.getReturnedRecord().into(Employee.class).getId();
            */
            id = dslContext.insertInto(EMPLOYEES)
                    .set(EMPLOYEES.NAME, employee.getName())
                    .set(EMPLOYEES.BIRTH_DATE, employee.getBirthDate())
                    .set(EMPLOYEES.EMAIL, employee.getEmail())
                    .set(EMPLOYEES.COMPANY_ID, employee.getCompany().getId())
                    .returning(EMPLOYEES.ID)
                    .fetchOptional()
                    .orElseThrow(() -> new DataAccessException("Error inserting entity: " + employee.getId()))
                    .getId();
        } catch (Exception e) {
            log.error(e.toString());
        }
        return id;
    }

    @Transactional
    public int update(Employee employee) {
        int result = 0;
        try {
            /* TODO - Produce BadSqlGrammarException, cause - add '"public"."employees".' to column names
            UpdateQuery<EmployeesRecord> updateQuery = dslContext.updateQuery(EMPLOYEES);
            updateQuery.addValues(getMap(employee));
            updateQuery.addConditions(EMPLOYEES.ID.eq(employee.getId()));
            result = updateQuery.execute();
             */
            result = dslContext.update(EMPLOYEES)
                    .set(DSL.field( "name"), employee.getName())
                    .set(DSL.field( "birth_date"), employee.getBirthDate())
                    .set(DSL.field( "email"), employee.getEmail())
                    .set(DSL.field( "company_id"), employee.getCompany().getId())
                    /* TODO - Produce BadSqlGrammarException, cause - add '"public"."employees".' to column names
                    .set(EMPLOYEES.NAME, employee.getName())
                    .set(EMPLOYEES.BIRTH_DATE, employee.getBirthDate())
                    .set(EMPLOYEES.EMAIL, employee.getEmail())
                    .set(EMPLOYEES.COMPANY_ID, employee.getCompany().getId())
                     */
                    .where(EMPLOYEES.ID.eq(employee.getId()))
                    .returning(EMPLOYEES.ID)
                    .fetchOptional()
                    .orElseThrow(() -> new DataAccessException("Error updating entity: " + employee.getId()))
                    .getId();
        } catch (Exception dae) {
            log.error(dae.toString());
        }
        return result;
    }

    private Map getMap(Employee employee) {
        return Map.of(
                "name", employee.getName(),
                "birth_date", employee.getBirthDate(),
                "email", employee.getEmail(),
                "company_id", employee.getCompany().getId());
    }

    public Employee get(int id) {
        Employee result = null;
        try {
            result = selectJoin().where(e.ID.eq(id)).fetchOne(mapper);
        } catch (Exception dae) {
            log.error(dae.toString());
        }
        return result;
    }

    public List<Employee> getAll() {
        List<Employee> result = null;
        try {
            result = selectJoin().fetch().stream().map(mapper::map).collect(Collectors.toList());
        } catch (Exception dae) {
            log.error(dae.toString());
        }
        return result;
    }

    public List<Employee> search(EmployeeHeaders columnName, String textToSearch) {
        Condition condition;
        String pattern = "%" + textToSearch.toLowerCase() + "%";
        switch (columnName) {
            case FULL_NAME:
                condition = e.NAME.lower().like(pattern);
                break;
            case BIRTH_DATE:
                condition = e.BIRTH_DATE.equal(LocalDate.parse(textToSearch));
                break;
            case EMAIL:
                condition = e.EMAIL.lower().like(pattern);
                break;
            case COMPANY:
                condition = c.NAME.equal(textToSearch);
                break;
            default:
                return getAll();
        }


        List<Employee> result = null;
        try {
            result = selectJoin().where(condition).fetch().stream().map(mapper::map).collect(Collectors.toList());
        } catch (Exception dae) {
            log.error(dae.toString());
        }
        return result;
    }

    @Transactional
    public boolean delete(int id) {
        boolean result = false;
        try {
            result = dslContext.deleteFrom(EMPLOYEES).where(EMPLOYEES.ID.eq(id)).execute() != 0;
        } catch (Exception dae) {
            log.error(dae.toString());
        }
        return result;
    }
}
