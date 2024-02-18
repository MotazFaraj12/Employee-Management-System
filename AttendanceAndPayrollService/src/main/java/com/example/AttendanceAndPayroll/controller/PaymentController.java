package com.example.AttendanceAndPayroll.controller;

import com.example.AttendanceAndPayroll.DTO.PaymentDTO;
import com.example.AttendanceAndPayroll.mapper.PaymentMapper;
import com.example.AttendanceAndPayroll.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Payments")
public class PaymentController {
    private final PaymentService paymentService;
    private final PaymentMapper mapper;
    //admin // all payments in a month of a year
    @GetMapping("/{year}/{month}")
    public ResponseEntity<List<PaymentDTO>> allPayments(@PathVariable String year , @PathVariable String month){
        return ResponseEntity.ok(mapper.PaymentListToPaymentDTOList(paymentService.payments(year,month)));
    }
    //admin // calculate this employee payment for this month
    @GetMapping("/Employees/{id}")
    public ResponseEntity<PaymentDTO> LatestEmployeePayment(@PathVariable Long id){
        return ResponseEntity.ok(mapper.PaymentToPaymentDTO(paymentService.GetLatestEmployeePayment(id)));
    }
    //admin // calculate this month of the year payments for all employees
    @GetMapping
    public ResponseEntity<List<PaymentDTO>> CalculateAllPaymentsForThisMonth(){
        return ResponseEntity.ok(mapper.PaymentListToPaymentDTOList(paymentService.CalculateAllPayments()));
    }
}