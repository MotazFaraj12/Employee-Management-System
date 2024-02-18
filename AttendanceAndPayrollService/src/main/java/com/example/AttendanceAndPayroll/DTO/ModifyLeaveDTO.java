package com.example.AttendanceAndPayroll.DTO;

import com.example.AttendanceAndPayroll.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ModifyLeaveDTO {
    private Status status;
}
