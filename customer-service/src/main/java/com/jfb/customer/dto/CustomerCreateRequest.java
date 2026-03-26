package com.jfb.customer.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * DTO para criação de um novo cliente
 */
public record CustomerCreateRequest(

        @NotBlank(message = "Nome completo é obrigatório")
        @Size(min = 3, max = 150, message = "Nome deve ter entre 3 e 150 caracteres")
        String fullName,

        @NotBlank(message = "CPF é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter exatamente 11 dígitos")
        String cpf,

        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        @NotNull(message = "Data de nascimento é obrigatória")
        @Past(message = "Data de nascimento deve ser no passado")
        LocalDate birthDate,

        @NotBlank(message = "Número de telefone é obrigatório")
        @Pattern(regexp = "\\+?\\d{10,15}", message = "Telefone inválido. Use formato internacional (ex: +5511999999999)")
        String phoneNumber,

        @NotNull(message = "Endereço é obrigatório")
        AddressRecord address
) {
}
