package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.DTO.PaymentDTO;
import com.example.EmployeeManagement.aspect.LogMethod;
import com.example.EmployeeManagement.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    @LogMethod
    public ResponseEntity<List<PaymentDTO>> PaymentHistory(Integer year , Integer month){
        return paymentRepository.allPayments(year, month);
    }
    @LogMethod
    public CompletableFuture<Mono<PaymentDTO>> LatestEmployeePayment(Long EmployeeId){
        return paymentRepository.LeftestEmployeePayment(EmployeeId);
    }
    @LogMethod
    public ResponseEntity<List<PaymentDTO>> AllEmployeePaymentsForThisMonth(){
        return paymentRepository.AllEmployeesPaymentsForThisMonthOfTheYear();
    }
}
