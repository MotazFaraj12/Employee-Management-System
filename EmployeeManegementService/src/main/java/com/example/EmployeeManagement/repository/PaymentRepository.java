package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.DTO.PaymentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
@RequiredArgsConstructor
public class PaymentRepository {
    private final WebClient webClient;
    public ResponseEntity<List<PaymentDTO>> allPayments(Integer year , Integer month){
        return webClient.get()
                .uri("/Payments/{year}/{month}", year,month)
                .retrieve()
                .toEntityList(PaymentDTO.class)
                .block();
    }
    @Async
    public CompletableFuture<Mono<PaymentDTO>> LeftestEmployeePayment(Long EmployeeId){
        return CompletableFuture.completedFuture(webClient.get()
                .uri("/Payments/Employees/{id}",EmployeeId)
                .retrieve()
                .bodyToMono(PaymentDTO.class));
    }
    public ResponseEntity<List<PaymentDTO>> AllEmployeesPaymentsForThisMonthOfTheYear(){
        return webClient.get()
                .uri("/Payments")
                .retrieve()
                .toEntityList(PaymentDTO.class)
                .block();
    }
}
