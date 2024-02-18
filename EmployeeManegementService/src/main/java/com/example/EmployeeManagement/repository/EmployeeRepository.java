package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.DTO.EmployeeProjection;
import com.example.EmployeeManagement.DTO.projectEmployee;
import com.example.EmployeeManagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
    void deleteByEmail(String email);
    @Query(value =  "SELECT e.firstName AS firstName, e.email AS email, e.id AS id " +
                    "FROM Employee e " +
                    "JOIN e.projects p " +
                    "GROUP BY e " +
                    "HAVING COUNT(p) > 1")
    List<EmployeeProjection> findEmployeesWithMultipleProjects();

    @Query(value = "select p.id , p.name , p.description , e.first_name , e.email from employeedatabase.employees e join employeedatabase.project p on e.id = p.id where e.email = ?1" , nativeQuery = true)
    List<projectEmployee> projectsForEmployee(String email);
    @Modifying
    @Query(value = "update employees set enabled = true where email = ?1" , nativeQuery = true)
    void enableAppUser(String email);
}
