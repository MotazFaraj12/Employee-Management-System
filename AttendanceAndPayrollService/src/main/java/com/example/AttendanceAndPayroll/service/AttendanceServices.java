package com.example.AttendanceAndPayroll.service;
import com.example.AttendanceAndPayroll.aspect.LogMethod;
import com.example.AttendanceAndPayroll.model.*;
import com.example.AttendanceAndPayroll.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendanceServices {
    private final AttendanceRepository attendanceRepository;
    /********************************Attendance********************************/
    @LogMethod
    public Attendance clockIn(Long id) {
            Attendance attendanceRecord = Attendance
                    .builder()
                    .employee_id(id)
                    .ClockInTime(LocalDateTime.now())
                    .year(LocalDate.now().getYear())
                    .month(LocalDate.now().getMonthValue())
                    .day(LocalDate.now().getDayOfMonth())
                    .leave(null)
                    .build();
            return attendanceRepository.save(attendanceRecord);
    }
    @LogMethod
    public Attendance clockOut(Long id) {
            Attendance latestRecordOptional = attendanceRepository.FindTheLatestForEmployee(id).orElseThrow(
                    ()-> new IllegalArgumentException("Employee has not clocked in.")
            );
            latestRecordOptional.setClockOutTime(LocalDateTime.now());
            return attendanceRepository.save(latestRecordOptional);
    }
}
