package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.model.ConfirmationToken;
import com.example.EmployeeManagement.model.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeEmailsGenerator {
    private final EmailService emailService;
    public void sendConfirmationEmail(Employee employee, ConfirmationToken confirmationToken){
        String title = "Confirm your email";
        String link = "http://localhost:8080/auth/register/confirm?token=" + confirmationToken.getToken();
        String massage = "Thank you for registering. Please click on the below link to activate your account:";
        String button = "Activate Now";
        String email = employee.getEmail();
        emailService.send(email, title, employee.getFirstName(), link, massage, 3, button);
    }

}
