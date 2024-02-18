package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.DTO.PaymentDTO;
import com.example.EmployeeManagement.service.AttendanceService;
import com.example.EmployeeManagement.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.CompletableFuture;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
@RequestMapping("/Employees")
public class AttendanceController {
    private final AttendanceService attendanceService;
    private final PaymentService paymentService;
    @PostMapping("/{id}/Attendance/ClockIn")
    public CompletableFuture<Mono<String>> ClockIn(@PathVariable Long id) {
        CompletableFuture<Mono<String>> clockInValueFuture = attendanceService.ClockIn(id);
        return clockInValueFuture.thenApply(clockInValue -> ResponseEntity.ok(clockInValue).getBody());
    }
    @PostMapping("/{id}/Attendance/ClockOut")
    public CompletableFuture<Mono<String>> ClockOut(@PathVariable Long id){
        CompletableFuture<Mono<String>> clockOutValueFuture =attendanceService.ClockOut(id);
        return clockOutValueFuture.thenApply(clockOutValue -> ResponseEntity.ok(clockOutValue).getBody());
    }
    @PostMapping("/{id}/Attendance/both")
    public CompletableFuture<Mono<String>> BothAPIs(@PathVariable Long id) {
        CompletableFuture<Mono<String>> future1 = attendanceService.ClockIn(id);
        CompletableFuture<Mono<PaymentDTO>> future2 = paymentService.LatestEmployeePayment(id);
        return future1.thenCombine(future2, (result1, result2) -> Mono.zip(result1, result2).map(tuple -> {
            String responseEntity1 = tuple.getT1();
            PaymentDTO responseEntity2 = tuple.getT2();
            return responseEntity1 + "\n" + responseEntity2;
        }));
    }
}
//JDBC
//jdbc template, batch insert and batch update
// ahmad.khateeb@quizplus.com