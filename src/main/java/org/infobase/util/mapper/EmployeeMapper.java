package org.infobase.util.mapper;

import org.infobase.model.Employee;
import org.infobase.to.EmployeeTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper  INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(target = "companyName", source = "employee.company.name")
    EmployeeTo toEmployeeTo(Employee employee);
}
