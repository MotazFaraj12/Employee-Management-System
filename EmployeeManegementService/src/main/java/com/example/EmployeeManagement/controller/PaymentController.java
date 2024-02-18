package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.DTO.PaymentDTO;
import com.example.EmployeeManagement.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Payments")
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/{year}/{month}")
    public ResponseEntity<List<PaymentDTO>> allPayments(@PathVariable Integer year,@PathVariable Integer month){
        return paymentService.PaymentHistory(year,month);
    }
    @GetMapping("/Employees/{id}")
    public CompletableFuture<Mono<PaymentDTO>> EmployeePayment(@PathVariable Long id){
        return paymentService.LatestEmployeePayment(id);
    }
    @GetMapping
    public ResponseEntity<List<PaymentDTO>> AllPaymentsInThisMonth(){
        return paymentService.AllEmployeePaymentsForThisMonth();
    }
}
