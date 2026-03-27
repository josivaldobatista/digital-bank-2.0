package com.jfb.account.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AccountCreateRequest(
        @NotNull(message = "Customer ID is required")
        UUID customerId
) {
}