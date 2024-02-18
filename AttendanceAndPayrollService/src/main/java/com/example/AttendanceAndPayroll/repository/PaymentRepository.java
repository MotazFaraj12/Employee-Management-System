package com.example.AttendanceAndPayroll.repository;
import com.example.AttendanceAndPayroll.DTO.PaymentProjection;
import com.example.AttendanceAndPayroll.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment , Long> {
    @Query(value = "select *\n" +
            "from attendanceandpayroll.payment\n" +
            "where year = ?1 and month = ?2 ;" , nativeQuery = true)
    List<Payment> FindPaymentsInMonthAndYear(Integer year , Integer month);

    @Query(value = "select distinct\n" +
            "    CURRENT_TIMESTAMP() as created_at,\n" +
            "    YEAR(CURRENT_TIMESTAMP()) as year,\n" +
            "    MONTH(CURRENT_TIMESTAMP()) as month,\n" +
            "    s.id as salary_id,\n" +
            "    (s.base_salary - (a.total_absent_hours * s.hourly_rate) + (e.total_overtime_hours * s.bonuses_factor * s.hourly_rate)) as total\n" +
            "from attendanceandpayroll.salary s\n" +
            "join\n" +
            "    (\n" +
            "    SELECT\n" +
            "        a.employee_id,\n" +
            "        (\n" +
            "            SUM(\n" +
            "                CASE\n" +
            "                    WHEN a.Leave_id is not null then\n" +
            "                        case\n" +
            "                            when l.status = 'ACCEPTED' and l.without_balance = b'1' then\n" +
            "                                case\n" +
            "                                    when l.half_day = b'0'THEN\n" +
            "                                        8\n" +
            "                                    else\n" +
            "                                        4\n" +
            "                                end\n" +
            "                        end\n" +
            "                    ELSE\n" +
            "                        case\n" +
            "                            when TIMESTAMPDIFF(HOUR, a.ClockInTime, a.ClockOutTime) > 8 then\n" +
            "                                0\n" +
            "                            else\n" +
            "                                8 - TIMESTAMPDIFF(HOUR, a.ClockInTime, a.ClockOutTime)\n" +
            "                        end\n" +
            "                    end\n" +
            "                )\n" +
            "            ) AS total_absent_hours\n" +
            "    FROM\n" +
            "        attendanceandpayroll.attendance a\n" +
            "    LEFT JOIN\n" +
            "        attendanceandpayroll.leaves l ON a.Leave_id = l.id\n" +
            "    WHERE\n" +
            "        a.month = ?2 -- local date time\n" +
            "        AND a.year = ?1 -- local date time\n" +
            "    GROUP BY\n" +
            "        a.employee_id, a.month, a.year\n" +
            "    )\n" +
            "        a on s.employee_id = a.employee_id\n" +
            "join\n" +
            "    (\n" +
            "    SELECT\n" +
            "        a.employee_id,\n" +
            "        SUM(\n" +
            "            CASE\n" +
            "                WHEN TIMESTAMPDIFF(HOUR, a.ClockInTime, a.ClockOutTime) > 8 THEN\n" +
            "                    TIMESTAMPDIFF(HOUR, a.ClockInTime, a.ClockOutTime) - 8\n" +
            "                ELSE\n" +
            "                    0\n" +
            "                END\n" +
            "            ) AS total_overtime_hours\n" +
            "    FROM\n" +
            "        attendanceandpayroll.attendance a\n" +
            "    WHERE\n" +
            "        a.month = ?2 -- local date time\n" +
            "        AND a.year = ?1 -- local date time\n" +
            "    GROUP BY\n" +
            "        a.employee_id, a.month, a.year\n" +
            "    )\n" +
            "        e on s.employee_id = e.employee_id\n" +
            "where s.effictive_to is null\n" +
            "GROUP BY salary_id ,total;", nativeQuery = true)
    List<PaymentProjection> getAllPaymentsForEmployees( Integer year , Integer month );

    @Query(value = "select *\n" +
            "from attendanceandpayroll.payment\n" +
            "where year = ?1 and month = ?2" , nativeQuery = true )
    List<Payment> findAll(Integer year , Integer month);

    @Query(value = "SELECT p.*\n" +
            "FROM attendanceandpayroll.payment p\n" +
            "JOIN (\n" +
            "    SELECT salary_id, MAX(created_at) AS latest_created_at\n" +
            "    FROM attendanceandpayroll.payment\n" +
            "    GROUP BY salary_id\n" +
            ") latest ON p.salary_id = latest.salary_id AND p.created_at = latest.latest_created_at\n" +
            "join attendanceandpayroll.salary s on p.salary_id = s.id\n" +
            "where s.employee_id = ?1;" , nativeQuery = true)
    Payment LatestEmployeePayment(Long employeeId);
}
