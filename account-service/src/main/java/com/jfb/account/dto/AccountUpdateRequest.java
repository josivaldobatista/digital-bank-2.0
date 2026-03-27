package com.jfb.account.dto;

import com.jfb.account.model.AccountStatus;
import jakarta.validation.constraints.NotNull;

public record AccountUpdateRequest(
        @NotNull(message = "Status is required")
        AccountStatus status
) {
}