package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.aspect.LogMethod;
import com.example.EmployeeManagement.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    @LogMethod
    public CompletableFuture<Mono<String>> ClockIn(Long id) {
        return attendanceRepository.clockIn(id);
    }
    @LogMethod
    public CompletableFuture<Mono<String>> ClockOut(Long id){
        return attendanceRepository.ClockOut(id);
    }
}