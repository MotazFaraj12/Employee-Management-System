package com.example.AttendanceAndPayroll.repository;

import com.example.AttendanceAndPayroll.model.LeaveLookup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface LeaveLookupRepository extends JpaRepository<LeaveLookup , Long> {
    @Query(value = "SELECT distinct ll.*\n" +
            "FROM attendanceandpayroll.leave_lookup ll\n" +
            "WHERE ll.employee_id = ?1 and ll.year= ?2", nativeQuery = true)
    Optional<LeaveLookup> FindTicketsForEmployeeInYear( Long id , Integer year);
}
