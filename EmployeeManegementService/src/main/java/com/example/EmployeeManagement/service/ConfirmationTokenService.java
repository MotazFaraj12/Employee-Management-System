package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.model.ConfirmationToken;
import com.example.EmployeeManagement.model.Employee;
import com.example.EmployeeManagement.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository repository;

    public ConfirmationToken createConfirmationToken(Employee employee) {

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = ConfirmationToken
                .builder()
                .token(token)
                .createDate(LocalDateTime.now())
                .expirationData(LocalDateTime.now().plusMinutes(3))
                .employee(employee)
                .build();

        return repository.save(confirmationToken);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return repository.findByToken(token);
    }

    @Transactional
    public void setConfirmedAt(String token) {
        repository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
