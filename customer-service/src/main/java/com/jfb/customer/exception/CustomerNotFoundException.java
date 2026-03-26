package com.jfb.customer.exception;

import org.springframework.http.HttpStatus;

/**
 * Exceção lançada quando um cliente não é encontrado.
 */
public class CustomerNotFoundException extends CustomerException {

    public CustomerNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
