package com.example.AttendanceAndPayroll.DTO;

import java.sql.Timestamp;

public interface PaymentProjection {
    Long getSalary_id();
    Long getTotal();
    Timestamp getCreated_at();
    Integer getYear();
    Integer getMonth();
}
