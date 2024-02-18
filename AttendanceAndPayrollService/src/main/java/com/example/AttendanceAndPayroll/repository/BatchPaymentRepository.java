package com.example.AttendanceAndPayroll.repository;

import com.example.AttendanceAndPayroll.DTO.PaymentProjection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class BatchPaymentRepository {
    private final JdbcTemplate jdbcTemplate;
    public BatchPaymentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Transactional
    public void saveAll(List<PaymentProjection> paymentList) {
        jdbcTemplate.batchUpdate("INSERT INTO attendanceandpayroll.payment (total, created_at, year, month, salary_id) \n" +
                        "VALUES (?, ?, ?, ?, ?)",
                paymentList,
                2,
                (PreparedStatement ps, PaymentProjection payment) -> {
                    ps.setLong(1, payment.getTotal());
                    ps.setTimestamp(2, Timestamp.valueOf(payment.getCreated_at().toLocalDateTime()));
                    ps.setInt(3,payment.getYear());
                    ps.setInt(4,payment.getMonth());
                    ps.setLong(5,payment.getSalary_id());
                });
    }
}