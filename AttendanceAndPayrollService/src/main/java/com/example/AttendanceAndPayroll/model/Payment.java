package com.example.AttendanceAndPayroll.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "Payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total")
    private Long total;

    @Column(name = "created_at")
    private Timestamp created_at;

    @Column(name = "month")
    private Integer month;

    @Column(name = "year")
    private Integer year;

    @JoinColumn(name = "salary_id")
    @ManyToOne
    private Salary salary;
}
