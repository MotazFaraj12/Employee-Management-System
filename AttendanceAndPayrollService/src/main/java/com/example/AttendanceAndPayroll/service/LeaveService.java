package com.example.AttendanceAndPayroll.service;

import com.example.AttendanceAndPayroll.aspect.LogMethod;
import com.example.AttendanceAndPayroll.model.*;
import com.example.AttendanceAndPayroll.repository.AttendanceRepository;
import com.example.AttendanceAndPayroll.repository.LeaveLookupRepository;
import com.example.AttendanceAndPayroll.repository.LeaveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveService {
    private final LeaveRepository leaveRepository;
    private final LeaveLookupRepository lookupRepository;
    private final AttendanceRepository attendanceRepository;

    @LogMethod
    public List<Leave> getEmployeeLeaves(Long id){
        return leaveRepository.ListAllTheLeaveForEmployee(id);
    }
    @LogMethod
    public List<Leave> getAllLeave(){
        return leaveRepository.GetAllPendingLeave();
    }
    @LogMethod
    public Leave RequestLeave(Leave leave){
        Long id = leave.getEmployeeId();
        LeaveLookup lookup = lookupRepository.FindTicketsForEmployeeInYear(id , LocalDate.now().getYear()).orElseThrow(
                ()-> new IllegalArgumentException("You dont have a leave look up")
        );
        leave.setLeaveLookup(lookup);
        return  leaveRepository.save(leave);
    }
    @LogMethod
    public Leave leaveStatus(Long id , Status status) {
        Leave leave = leaveRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("This employee dose not have a leave request")
        );
        if (status.equals(Status.REJECTED)) {
            leave.setStatus(Status.REJECTED);
            return leaveRepository.save(leave);
        }
        LeaveLookup tickets = leave.getLeaveLookup();
        Double deduction = leave.isHalf_day() ? 0.5 : 1.0;
        if ((leave.getLeave() == Leave_enum.SICK && tickets.getSick_leaves() > 0)
                || (leave.getLeave() == Leave_enum.CASUAL && tickets.getCasual_leaves() > 0)) {
            if (leave.getLeave() == Leave_enum.SICK) {
                tickets.setSick_leaves(tickets.getSick_leaves() - deduction);
            } else {
                tickets.setCasual_leaves(tickets.getCasual_leaves() - deduction);
            }
        } else {
            leave.setWithout_balance(true);
        }
        leave.setStatus(Status.ACCEPTED);
        attendanceRepository.save(makeAttendance(id,leave));
        lookupRepository.save(tickets);
        return leaveRepository.save(leave);
    }
    @LogMethod
    private Attendance makeAttendance(Long id,Leave leave){
        return Attendance
                .builder()
                .employee_id(id)
                .ClockInTime(null)
                .ClockOutTime(null)
                .leave(leave)
                .year(LocalDate.now().getYear())
                .month(LocalDate.now().getMonthValue())
                .day(LocalDate.now().getDayOfMonth())
                .build();
    }
}
