package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.model.PerformanceReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview ,Long> {
}
