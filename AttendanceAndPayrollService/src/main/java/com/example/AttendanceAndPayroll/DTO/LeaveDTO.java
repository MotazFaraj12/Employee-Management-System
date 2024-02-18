package com.example.AttendanceAndPayroll.DTO;

import com.example.AttendanceAndPayroll.model.Leave_enum;
import com.example.AttendanceAndPayroll.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LeaveDTO {
    private Long id;
    private boolean half_day;
    private boolean without_balance;
    private Status status;
    private Leave_enum leave;
    private Timestamp created_at;
    private Timestamp updated_at;
}
