package com.example.AttendanceAndPayroll.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ClockOutTime")
    private LocalDateTime ClockOutTime;

    @Column(name = "ClockInTime")
    private LocalDateTime ClockInTime;

    @Column(name = "employee_id")
    private Long employee_id;

    @Column(name = "year")
    private Integer year;

    @Column(name = "month")
    private Integer month;

    @Column(name = "day")
    private Integer day;

    @OneToOne
    @JoinColumn(name = "Leave_id")
    private Leave leave;
}
