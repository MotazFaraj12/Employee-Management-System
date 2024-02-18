package com.example.EmployeeManagement.mapper;

import com.example.EmployeeManagement.DTO.RegisterRequest;
import com.example.EmployeeManagement.DTO.RegisterResponse;
import com.example.EmployeeManagement.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
   @Mapping(target = "isDeleted" , constant = "false")
   @Mapping(target = "isVerified", constant = "false")
   @Mapping(target = "role", expression = "java(com.example.EmployeeManagement.model.Roles.USER)")
   @Mapping(target = "firstName" , source = "firstName")
   @Mapping(target = "lastName" , source = "lastName")
   Employee RequerstToEmployee(RegisterRequest request);

}