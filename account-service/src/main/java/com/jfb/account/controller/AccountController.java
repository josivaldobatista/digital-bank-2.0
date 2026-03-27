package com.jfb.account.controller;

import com.jfb.account.dto.AccountCreateRequest;
import com.jfb.account.dto.AccountResponse;
import com.jfb.account.dto.AccountUpdateRequest;
import com.jfb.account.dto.BalanceResponse;
import com.jfb.account.mapper.AccountMapper;
import com.jfb.account.model.Account;
import com.jfb.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @PostMapping
    public ResponseEntity<AccountResponse> create(
            @Valid @RequestBody AccountCreateRequest request
    ) {
        Account account = accountService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountMapper.toResponse(account));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getById(@PathVariable UUID id) {
        Account account = accountService.findById(id);
        return ResponseEntity.ok(accountMapper.toResponse(account));
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable UUID id) {
        Account account = accountService.findById(id);
        return ResponseEntity.ok(accountMapper.toBalanceResponse(account));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AccountResponse> updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody AccountUpdateRequest request
    ) {
        Account account = accountService.updateStatus(id, request);
        return ResponseEntity.ok(accountMapper.toResponse(account));
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<Void> deposit(
            @PathVariable UUID id,
            @RequestParam BigDecimal amount
    ) {
        accountService.deposit(id, amount);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Void> withdraw(
            @PathVariable UUID id,
            @RequestParam BigDecimal amount
    ) {
        accountService.withdraw(id, amount);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/block")
    public ResponseEntity<Void> blockAmount(
            @PathVariable UUID id,
            @RequestParam BigDecimal amount
    ) {
        accountService.blockAmount(id, amount);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/unblock")
    public ResponseEntity<Void> unblockAmount(
            @PathVariable UUID id,
            @RequestParam BigDecimal amount
    ) {
        accountService.unblockAmount(id, amount);
        return ResponseEntity.noContent().build();
    }
}