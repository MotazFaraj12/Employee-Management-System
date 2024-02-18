package com.example.EmployeeManagement.mapper;

import com.example.EmployeeManagement.model.Employee;
import com.example.EmployeeManagement.security.model.AuthUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthUserMapper {
    AuthUser EmployeeToAuthUser(Employee employee);
}
