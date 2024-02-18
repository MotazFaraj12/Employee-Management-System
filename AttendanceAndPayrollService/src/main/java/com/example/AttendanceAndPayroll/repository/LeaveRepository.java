package com.example.AttendanceAndPayroll.repository;

import com.example.AttendanceAndPayroll.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveRepository extends JpaRepository<Leave , Long> {
    @Query(value = "SELECT l.*\n" +
            "FROM attendanceandpayroll.leave_lookup AS ll\n" +
            "JOIN attendanceandpayroll.leaves AS l ON ll.id = l.leave_lookup_id\n" +
            "WHERE l.status = 'PENDING' and ll.employee_id = ?1" , nativeQuery = true)
    Optional<Leave> FineLeaveForEmployee(Long id);

    @Query(value = "select leaves.*\n" +
            "from attendanceandpayroll.leave_lookup\n" +
            "join attendanceandpayroll.leaves on leave_lookup.id = leaves.leave_lookup_id\n" +
            "where employee_id = ?1" , nativeQuery = true)
    List<Leave> ListAllTheLeaveForEmployee(Long id);

    @Query(value = "select *\n" +
            "from attendanceandpayroll.leaves\n" +
            "where status = 'PENDING'" , nativeQuery = true)
    List<Leave> GetAllPendingLeave();
}
