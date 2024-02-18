package com.example.AttendanceAndPayroll.repository;

import com.example.AttendanceAndPayroll.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface SalaryRepository extends JpaRepository<Salary , Long> {
    @Query(value = "select *\n" +
            "from attendanceandpayroll.salary\n" +
            "where employee_id = ?1 and effictive_to IS NULL;",nativeQuery = true)
    Optional<Salary> TheEmployeeBaseSalary(Long employee_id);

    @Query(value = "select *\n" +
            "from attendanceandpayroll.salary\n" +
            "where effictive_to is null" , nativeQuery = true)
    List<Salary> AllEmployeesSalarys();
}
