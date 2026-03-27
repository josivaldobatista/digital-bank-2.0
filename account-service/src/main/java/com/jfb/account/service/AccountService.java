package com.jfb.account.service;

import com.jfb.account.dto.AccountCreateRequest;
import com.jfb.account.dto.AccountUpdateRequest;
import com.jfb.account.model.Account;
import com.jfb.account.model.AccountStatus;
import com.jfb.account.model.AccountType;
import com.jfb.account.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    @Transactional
    public Account create(AccountCreateRequest request) {
        Account account = Account.builder()
                .accountNumber(generateAccountNumber())
                .customerId(request.customerId())
                .accountType(AccountType.CHECKING)
                .status(AccountStatus.ACTIVE)
                .build();
        return accountRepository.save(account);
    }

    public Account findById(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    @Transactional
    public Account updateStatus(UUID accountId, AccountUpdateRequest request) {
        Account account = findById(accountId);
        account.setStatus(request.status());
        return accountRepository.save(account);
    }

    @Transactional
    public void deposit(UUID accountId, BigDecimal amount) {
        Account account = accountRepository.findWithLockById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.deposit(amount);
    }

    @Transactional
    public void withdraw(UUID accountId, BigDecimal amount) {
        Account account = accountRepository.findWithLockById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.withdraw(amount);
    }

    @Transactional
    public void blockAmount(UUID accountId, BigDecimal amount) {
        Account account = accountRepository.findWithLockById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.blockAmount(amount);
    }

    @Transactional
    public void unblockAmount(UUID accountId, BigDecimal amount) {
        Account account = accountRepository.findWithLockById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.unblockAmount(amount);
    }

    private String generateAccountNumber() {
// Simples (produção: usar algo mais robusto)
        return "ACC-" + String.format("%010d", System.currentTimeMillis() % 1_000_000_000);
    }
}