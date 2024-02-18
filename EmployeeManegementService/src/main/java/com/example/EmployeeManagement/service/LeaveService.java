package com.example.EmployeeManagement.service;

import com.example.EmployeeManagement.DTO.LeaveDTO;
import com.example.EmployeeManagement.DTO.LeaveRequest;
import com.example.EmployeeManagement.DTO.ModifyLeaveDTO;
import com.example.EmployeeManagement.aspect.LogMethod;
import com.example.EmployeeManagement.repository.LeaveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveService {
    private final LeaveRepository leaveRepository;

    @LogMethod
    public ResponseEntity<List<LeaveDTO>> getEmployeeLeaves(Long employeeId){
        return leaveRepository.getEmployeeLeaves(employeeId);
    }
    @LogMethod
    public ResponseEntity<List<LeaveDTO>> getAllLeaveForAllEmployeeWithPendingStatus(){
        return leaveRepository.getAllLeaveForAllEmployeeWithPendingStatus();
    }
    @LogMethod
    public ResponseEntity<LeaveDTO> leaveStatus(Long id , ModifyLeaveDTO modifyLeaveDTO){
        return leaveRepository.leaveStatus(id, modifyLeaveDTO);
    }
    @LogMethod
    public ResponseEntity<LeaveDTO> RequestLeave(LeaveRequest leaveRequest){
        return leaveRepository.RequestLeave(leaveRequest);
    }
}
