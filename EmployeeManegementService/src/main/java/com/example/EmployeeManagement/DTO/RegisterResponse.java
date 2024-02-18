package com.example.EmployeeManagement.DTO;

import com.example.EmployeeManagement.model.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {
    private String firstName;
    private String lastName;
    private String email;
    private Roles role;
}
