package com.example.EmployeeManagement.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.concurrent.CompletableFuture;

@Repository
@RequiredArgsConstructor
public class AttendanceRepository {
    private final WebClient webClient;
    @Async
    public CompletableFuture<Mono<String>> clockIn(Long id) {
        return CompletableFuture.completedFuture(webClient.post()
                .uri("/Attendances/Employees/{id}/ClockIn", id)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class));
    }
    @Async
    public CompletableFuture<Mono<String>> ClockOut(Long id){
        return CompletableFuture.completedFuture(webClient.put()
                .uri("/Attendances/Employees/{id}/ClockOut", id)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class));
    }
}