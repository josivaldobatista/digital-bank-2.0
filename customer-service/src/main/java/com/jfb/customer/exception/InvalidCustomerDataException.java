package com.jfb.customer.exception;

import org.springframework.http.HttpStatus;

/**
 * Exceção para regras de negócio inválidas (idade, CPF inválido, etc).
 */
public class InvalidCustomerDataException extends CustomerException {

    public InvalidCustomerDataException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
