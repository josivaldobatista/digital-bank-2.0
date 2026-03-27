package com.jfb.account.repository;

import com.jfb.account.model.Account;
import com.jfb.account.model.AccountStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findByCustomerId(UUID customerId);

    List<Account> findByCustomerIdAndStatus(UUID customerId, AccountStatus status);

    boolean existsByAccountNumber(String accountNumber);

    boolean existsByCustomerIdAndStatus(UUID customerId, AccountStatus status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Account a WHERE a.id = :id")
    Optional<Account> findWithLockById(UUID id);
}