package com.example.EmployeeManagement.DTO;

import com.example.EmployeeManagement.model.Status;
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
