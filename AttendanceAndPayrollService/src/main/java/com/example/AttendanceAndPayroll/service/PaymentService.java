package com.example.AttendanceAndPayroll.service;

import com.example.AttendanceAndPayroll.aspect.LogMethod;
import com.example.AttendanceAndPayroll.model.Payment;
import com.example.AttendanceAndPayroll.repository.BatchPaymentRepository;
import com.example.AttendanceAndPayroll.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final BatchPaymentRepository batchPaymentRepository;
    @LogMethod
    public List<Payment> payments(String year , String month){
        return paymentRepository.FindPaymentsInMonthAndYear(Integer.parseInt(year),Integer.parseInt(month));
    }
    @LogMethod
    public Payment GetLatestEmployeePayment(Long id){
        return paymentRepository.LatestEmployeePayment(id);
    }
    @LogMethod
    public List<Payment> CalculateAllPayments(){
        Integer year =  LocalDate.now().getYear();
        Integer month = LocalDate.now().getMonthValue();
        batchPaymentRepository.saveAll(paymentRepository.getAllPaymentsForEmployees(year,month));
        return paymentRepository.findAll(year,month);
    }
}