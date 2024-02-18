package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.DTO.LeaveDTO;
import com.example.EmployeeManagement.DTO.LeaveRequest;
import com.example.EmployeeManagement.DTO.ModifyLeaveDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LeaveRepository {
    private final WebClient webClient;
    public ResponseEntity<List<LeaveDTO>> getEmployeeLeaves(Long employeeId) {
        return webClient.get()
                .uri("/leaves/{employeeId}", employeeId)
                .retrieve()
                .toEntityList(LeaveDTO.class)
                .block();
    }
    public ResponseEntity<List<LeaveDTO>> getAllLeaveForAllEmployeeWithPendingStatus() {
        return webClient.get()
                .uri("/leaves/pending")
                .retrieve()
                .toEntityList(LeaveDTO.class)
                .block();
    }
    public ResponseEntity<LeaveDTO> leaveStatus(Long leaveID, ModifyLeaveDTO modifyLeaveDTO) {
        return webClient.post()
                .uri("/leaves/{LeaveID}", leaveID)
                .body(BodyInserters.fromValue(modifyLeaveDTO))
                .retrieve()
                .toEntity(LeaveDTO.class)
                .block();
    }
    public ResponseEntity<LeaveDTO> RequestLeave(LeaveRequest leaveRequest) {
        return webClient.post()
                .uri("/leaves/Request")
                .body(BodyInserters.fromValue(leaveRequest))
                .retrieve()
                .toEntity(LeaveDTO.class)
                .block();
    }
}
