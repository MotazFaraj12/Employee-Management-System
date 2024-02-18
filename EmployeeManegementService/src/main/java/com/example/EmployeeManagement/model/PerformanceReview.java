package com.example.EmployeeManagement.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "performance_review")
public class PerformanceReview {
        @Id
        @Column(name = "id")
        @GeneratedValue(strategy =  GenerationType.IDENTITY)
        private Long id;
        @Column(name = "date")
        private Date date;
        @Column(name = "comments")
        private String comments;

        @ManyToOne
        @JoinColumn(name = "employee_id")
        private Employee employee;

        @ManyToOne
        @JoinColumn(name = "reviewer_id")
        private Employee reviewer;

        public void setEmployee(Employee employee) {
            this.employee = employee;
        }

        public void setReviewer(Employee reviewer) {
            this.reviewer = reviewer;
        }
}