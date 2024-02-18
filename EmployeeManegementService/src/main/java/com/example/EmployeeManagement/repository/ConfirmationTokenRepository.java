package com.example.EmployeeManagement.repository;

import com.example.EmployeeManagement.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,Long> {
    Optional<ConfirmationToken> findByToken(String token);
    @Modifying
    @Query(value = "update confirmationtoken set confirmationDate =?2 where token=?1\n" , nativeQuery = true)
    void updateConfirmedAt(String token, LocalDateTime confirmedAt);
}
//webclint
//async,futher