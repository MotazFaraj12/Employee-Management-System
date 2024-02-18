package com.example.AttendanceAndPayroll.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PaymentDTO {
    private Long id;
    private Long total;
    private Integer month;
    private Integer year;
}
