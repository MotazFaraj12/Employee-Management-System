package com.example.AttendanceAndPayroll.controller;

import com.example.AttendanceAndPayroll.model.Attendance;
import com.example.AttendanceAndPayroll.service.AttendanceServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Attendances")
public class AttendanceController {
    private final AttendanceServices service;
    //all
    @PostMapping("/Employees/{id}/ClockIn")
    public ResponseEntity<String> ClockIn(@PathVariable Long id){
        Attendance attendance = service.clockIn(id);
        return ResponseEntity.ok("Clocked in at:" + attendance.getClockInTime() );
    }
    //all
    @PutMapping("/Employees/{id}/ClockOut")
    public ResponseEntity<String> ClockOut(@PathVariable Long id){
        Attendance attendance = service.clockOut(id);
        return ResponseEntity.ok("Clocked out at:" + attendance.getClockOutTime());
    }
}
