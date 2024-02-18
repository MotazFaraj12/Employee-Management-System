package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.DTO.LeaveDTO;
import com.example.EmployeeManagement.DTO.LeaveRequest;
import com.example.EmployeeManagement.DTO.ModifyLeaveDTO;
import com.example.EmployeeManagement.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Employees")
public class LeaveController {
    private final LeaveService leaveService;

    @GetMapping("/Leaves/{employee}")
    public ResponseEntity<List<LeaveDTO>> AllEmployeeLeave(@PathVariable Long employee){
        return leaveService.getEmployeeLeaves(employee);
    }
    @GetMapping("/leaves/pending")
    public ResponseEntity<List<LeaveDTO>> AllPendingLeaves(){
        return leaveService.getAllLeaveForAllEmployeeWithPendingStatus();
    }
    @PostMapping("/Leave/{LeaveID}/Status")
    public ResponseEntity<LeaveDTO> LeaveStatus(@PathVariable Long LeaveID ,@RequestBody ModifyLeaveDTO modifyLeaveDTO){
        return leaveService.leaveStatus(LeaveID, modifyLeaveDTO);
    }
    @PostMapping("/Leave/Request")
    public ResponseEntity<LeaveDTO> RequestLeave(@RequestBody LeaveRequest leaveRequest){
        return leaveService.RequestLeave(leaveRequest);
    }
}
