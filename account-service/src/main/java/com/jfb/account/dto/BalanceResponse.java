package com.jfb.account.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record BalanceResponse(
        UUID accountId,
        BigDecimal balance,
        BigDecimal blockedAmount,
        BigDecimal availableBalance
) {
}