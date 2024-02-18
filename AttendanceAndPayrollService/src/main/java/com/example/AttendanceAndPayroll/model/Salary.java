package com.example.AttendanceAndPayroll.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "salary")
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(name = "base_salary")
    private Long base_salary;

    @Column(name = "hourly_rate")
    private Long hourly_rate;

    @Column(name = "bonuses_factor")
    private Long bonuses_factor;

    @Column(name = "employee_id")
    private Long employee_id;

    @Column(name = "effictive_form")
    private Timestamp effictive_form;

    @Column(name = "effictive_to")
    private Timestamp effictive_to;

    @OneToMany(mappedBy = "salary")
    private List<Payment> EmployeePayments = new ArrayList<>();
}
