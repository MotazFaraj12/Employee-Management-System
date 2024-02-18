package com.example.EmployeeManagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "confirmationtoken")
public class ConfirmationToken {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "token")
    private String token;
    @Column(name = "createDate")
    private LocalDateTime createDate;
    @Column(name = "expirationData")
    private LocalDateTime expirationData;
    @Column(name = "confirmationDate")
    private LocalDateTime confirmationDate;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
