package com.jfb.customer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Record para endereço - usado tanto em request quanto em response
 */
public record AddressRecord(

        @NotBlank(message = "Rua é obrigatória")
        @Size(max = 150)
        String street,

        @NotBlank(message = "Número é obrigatório")
        @Size(max = 20)
        String number,

        @Size(max = 100)
        String complement,

        @NotBlank(message = "Bairro é obrigatório")
        @Size(max = 100)
        String neighborhood,

        @NotBlank(message = "Cidade é obrigatória")
        @Size(max = 100)
        String city,

        @NotBlank(message = "Estado (UF) é obrigatório")
        @Pattern(regexp = "[A-Z]{2}", message = "UF deve conter exatamente 2 letras maiúsculas (ex: SP)")
        String state,

        @NotBlank(message = "CEP é obrigatório")
        @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP inválido. Formato: 01234-567 ou 01234567")
        String zipCode
) {
}