package com.jfb.account.dto;

import com.jfb.account.model.AccountStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record AccountResponse(
        UUID id,
        String accountNumber,
        UUID customerId,
        String customerName,
        AccountStatus status,
        BigDecimal balance,
        BigDecimal availableBalance,
        Instant createdAt
) {
}