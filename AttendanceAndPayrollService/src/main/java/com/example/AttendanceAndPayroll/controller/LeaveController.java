package com.example.AttendanceAndPayroll.controller;

import com.example.AttendanceAndPayroll.DTO.LeaveDTO;
import com.example.AttendanceAndPayroll.DTO.LeaveRequest;
import com.example.AttendanceAndPayroll.DTO.ModifyLeaveDTO;
import com.example.AttendanceAndPayroll.mapper.LeaveMapper;
import com.example.AttendanceAndPayroll.model.Leave;
import com.example.AttendanceAndPayroll.model.Status;
import com.example.AttendanceAndPayroll.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/leaves")
public class LeaveController {
    private final LeaveService service;
    private final LeaveMapper mapper;
    @GetMapping("/{employeeId}")
    public ResponseEntity<List<LeaveDTO>> getEmployeeLeaves(@PathVariable Long employeeId){
        return ResponseEntity.ok(mapper.LeaveListToLeaveDTOList(service.getEmployeeLeaves(employeeId)));
    }
    @GetMapping("/pending")
    public ResponseEntity<List<LeaveDTO>> getAllLeaveForAllEmployeeWithPendingStatus(){
        return ResponseEntity.ok(mapper.LeaveListToLeaveDTOList(service.getAllLeave()));
    }
    @PostMapping("/{LeaveID}")
    public ResponseEntity<LeaveDTO> LeaveStatus(@PathVariable Long LeaveID, @RequestBody ModifyLeaveDTO modifyLeaveDTO){
        return ResponseEntity.ok(mapper.LeaveToLeaveDTO(service.leaveStatus(LeaveID, modifyLeaveDTO.getStatus())));
    }
    @PostMapping("/Request")
    public ResponseEntity<LeaveDTO> RequestLeave(@RequestBody LeaveRequest leaveRequest){
        return ResponseEntity.ok(mapper.LeaveToLeaveDTO(CreateLeaveRequest(leaveRequest)));
    }
    private Leave CreateLeaveRequest(LeaveRequest leaveRequest){
        Leave leave = mapper.LeaveRequestToLeave(leaveRequest);
        leave.setStatus(Status.PENDING);
        leave.setLeaveLookup(null);
        leave.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        return service.RequestLeave(leave);
    }
}
