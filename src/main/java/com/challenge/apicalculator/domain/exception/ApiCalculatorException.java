package com.challenge.apicalculator.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Excepción base para la aplicación
 */
@Getter
public class ApiCalculatorException extends RuntimeException {
    
    private final HttpStatus status;
    private final String code;
    private final String message;

    public ApiCalculatorException(HttpStatus status, String code, String message) {
        super(message);
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public ApiCalculatorException(HttpStatus status, String message) {
        this(status, status.name(), message);
    }
} 