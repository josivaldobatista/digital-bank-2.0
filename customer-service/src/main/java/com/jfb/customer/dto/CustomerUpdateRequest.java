package com.jfb.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO para atualização de cliente (campos opcionais)
 */
public record CustomerUpdateRequest(

        @Size(min = 3, max = 150, message = "Nome deve ter entre 3 e 150 caracteres")
        String fullName,

        @Email(message = "E-mail inválido")
        String email,

        @Pattern(regexp = "\\+?\\d{10,15}", message = "Telefone inválido")
        String phoneNumber,

        AddressRecord address
) {
}
