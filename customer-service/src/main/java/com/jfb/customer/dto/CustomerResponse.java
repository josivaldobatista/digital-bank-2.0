package com.jfb.customer.dto;

import com.jfb.customer.model.CustomerStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO de resposta para o cliente (não expõe dados sensíveis desnecessários)
 */
public record CustomerResponse(

        UUID id,
        String customerCode,
        String fullName,
        String cpf,
        String email,
        LocalDate birthDate,
        String phoneNumber,
        AddressRecord address,
        CustomerStatus status,
        Instant createdAt,
        Instant updatedAt,
        Instant deactivatedAt,
        String deactivatedReason
) {
}
