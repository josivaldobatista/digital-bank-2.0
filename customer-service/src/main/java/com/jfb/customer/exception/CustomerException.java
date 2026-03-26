package com.jfb.customer.exception;

import org.springframework.http.HttpStatus;

/**
 * Classe base para todas as exceções de domínio do customer-service.
 * Permite padronizar o tratamento de erros.
 */
public abstract class CustomerException extends RuntimeException {

    private final HttpStatus status;

    protected CustomerException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
