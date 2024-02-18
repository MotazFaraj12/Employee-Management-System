package com.example.EmployeeManagement.DTO;

import com.example.EmployeeManagement.model.Leave_enum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LeaveRequest {
    private Long employeeId;
    private Leave_enum leave;
    private Boolean half_day;
    private Boolean without_balance;
}
