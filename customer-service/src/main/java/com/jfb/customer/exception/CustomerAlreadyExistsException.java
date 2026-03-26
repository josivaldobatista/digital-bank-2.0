package com.jfb.customer.exception;

import org.springframework.http.HttpStatus;

/**
 * Exceção lançada quando tenta cadastrar um cliente com CPF ou e-mail já existente.
 */
public class CustomerAlreadyExistsException extends CustomerException {

    public CustomerAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
