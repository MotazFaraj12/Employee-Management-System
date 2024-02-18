package com.example.AttendanceAndPayroll.repository;

import com.example.AttendanceAndPayroll.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
     @Query(value = "SELECT *\n" +
             "FROM attendanceandpayroll.attendance\n" +
             "WHERE employee_id = ?1\n" +
             "ORDER BY ClockInTime DESC\n" +
             "LIMIT 1;" , nativeQuery = true)
     Optional<Attendance> FindTheLatestForEmployee(Long id);
     @Query(value = "select *\n" +
             "from attendanceandpayroll.attendance\n" +
             "where employee_id = ?1 and year =?2 and month = ?3" , nativeQuery = true)
     List<Attendance> EmployeeAttendance(Long id,Integer year,Integer month);
}