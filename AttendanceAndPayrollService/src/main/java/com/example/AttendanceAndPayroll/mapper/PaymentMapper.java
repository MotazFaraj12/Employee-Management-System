package com.example.AttendanceAndPayroll.mapper;

import com.example.AttendanceAndPayroll.DTO.PaymentDTO;
import com.example.AttendanceAndPayroll.model.Payment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    List<PaymentDTO> PaymentListToPaymentDTOList(List<Payment> payment);

    PaymentDTO PaymentToPaymentDTO(Payment payment);
}
